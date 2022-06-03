package org.thekiddos.utils;

import java.util.ArrayList;
import java.util.List;

public final class Simplex {
    private Simplex() {
    }

    private static int numOfVar = 0, m = 0, rows = 0, cols = 0;
    private static ArrayList<ArrayList<Double>> mat = null;
    private static ArrayList<Integer> b;
    private static boolean max = true;

    public static boolean input( int numOfVar, String objective, boolean isMax, String[] equations ) {
        b = new ArrayList<>();
        max = isMax;
        Simplex.numOfVar = numOfVar; // for constants and objective slack
        m = equations.length;
        cols = Simplex.numOfVar + m + 2;
        for ( String equation : equations )
            if ( !equation.contains( "<" ) && !equation.contains( ">" ) && equation.contains( "=" ) ) {
                ++cols;
                ++m;
            }
        rows = m + 1;

        mat = new ArrayList<>( rows );
        for ( int i = 0; i < rows; ++i )
            mat.add( new ArrayList<>( cols ) );

        for ( int i = 0; i < rows; ++i )
            for ( int j = 0; j < cols; ++j )
                mat.get( i ).add( 0.0 );

        ArrayList<Double> tmp = parseEquation( objective );
        if ( !isMax ) for ( int i = 0; i < cols; ++i )
            mat.get( rows - 1 ).set( i, tmp.get( i ) );
        else for ( int i = 0; i < cols; ++i )
            mat.get( rows - 1 ).set( i, -tmp.get( i ) );
        mat.get( rows - 1 ).set( cols - 1, -mat.get( rows - 1 ).get( cols - 1 ) );
        mat.get( rows - 1 ).set( cols - 2, 1.0 );

        for ( int i = 0, k = 0; i < equations.length; ++i, ++k ) {
            int index = equations[ i ].indexOf( '=' );
            for ( int j = index; j < equations[ i ].length(); ++j )
                if ( equations[ i ].charAt( j ) == '-' )
                    index = -1;
            if ( index != -1 )
                equations[ i ] = equations[ i ].replace( "=", "=+" );

            tmp = parseEquation( equations[ i ] );

            if ( equations[ i ].contains( "<" ) ) {
                for ( int j = 0; j < cols; ++j )
                    mat.get( k ).set( j, tmp.get( j ) );
                mat.get( k ).set( Simplex.numOfVar + k, 1.0 );
            } else if ( equations[ i ].contains( ">" ) ) {
                for ( int j = 0; j < cols; ++j )
                    mat.get( k ).set( j, -tmp.get( j ) );
                mat.get( k ).set( Simplex.numOfVar + k, 1.0 );
            } else if ( equations[ i ].contains( "=" ) ) {
                for ( int j = 0; j < cols; ++j )
                    mat.get( k ).set( j, tmp.get( j ) );
                mat.get( k ).set( Simplex.numOfVar + k, 1.0 );
                ++k;
                for ( int j = 0; j < cols; ++j )
                    mat.get( k ).set( j, -tmp.get( j ) );
                mat.get( k ).set( Simplex.numOfVar + k, 1.0 );
            }
        }
        return true;
    }

    private static int pivotColumn() {
        ArrayList<Double> tmp = mat.get( rows - 1 );
        Double mn = tmp.get( 0 );
        int mni = 0;
        for ( int i = 1; i < cols - 1; ++i )
            if ( tmp.get( i ) < mn ) {
                mn = tmp.get( i );
                mni = i;
            }
        if ( mn >= 0 )
            return -1;
        return mni;
    }

    private static int pivotRow( final int pc ) {
        double mn = 2e18;
        int mni = -1;
        for ( int i = 0; i < rows - 1; ++i )
            if ( mat.get( i ).get( pc ) > 0 && mn > mat.get( i ).get( cols - 1 ) / mat.get( i ).get( pc ) ) {
                mn = mat.get( i ).get( cols - 1 ) / mat.get( i ).get( pc );
                mni = i;
            } else if ( mat.get( i ).get( pc ) * mat.get( i ).get( cols - 1 ) > 0 && mat.get( i ).get( numOfVar + i ) == 1.0 && mn > mat.get( i ).get( cols - 1 ) / mat.get( i ).get( pc ) ) {
                mn = mat.get( i ).get( cols - 1 ) / mat.get( i ).get( pc );
                mni = i;
            }
        return mni;
    }

    private static int negativeb() {
        for ( int i = 0; i < rows - 1; ++i )
            if ( mat.get( i ).get( cols - 1 ) < 0 )
                return i;
        return -1;
    }

    // Fix for choosing the right one
    private static int pivotColumn( final int row ) {
        for ( int i = 0; i < cols - 1; ++i )
            if ( mat.get( row ).get( i ) < 0 )
                return i;
        return -1;
    }

    private static void pivot( final int pr, final int pc ) {
        final Double pe = mat.get( pr ).get( pc );
        for ( int i = 0; i < cols; ++i )
            mat.get( pr ).set( i, mat.get( pr ).get( i ) / pe );
    }

    private static void fixMat( final int pr, final int pc ) {
        for ( int i = 0; i < rows; ++i ) {
            if ( i == pr )
                continue;
            double mul = mat.get( i ).get( pc );
            for ( int j = 0; j < cols; ++j )
                mat.get( i ).set( j, mat.get( i ).get( j ) - mul * mat.get( pr ).get( j ) );
        }
    }

    private static boolean check() {
        boolean posb, negc;
        for ( int i = 0; i < rows; ++i ) {
            posb = mat.get( i ).get( cols - 1 ) > 0;
            negc = true;
            for ( int j = 0; j < cols - 1; ++j ) {
                negc &= mat.get( i ).get( j ) < 0;
            }
            if ( posb && negc )
                return false;
        }
        return true;
    }

    private static boolean inf( final int pc ) {
        for ( int i = 0; i < numOfVar; ++i )
            if ( mat.get( i ).get( pc ) > 0 )
                return false;
        return true;
    }

    public static List<Double> solve() {
        List<Double> solution = new ArrayList<>( numOfVar );
        for ( int i = 0; i < numOfVar; ++i ) {
            solution.add( 0.0 );
        }
        int pc, pr = 0, row;

        row = negativeb();
        if ( row != -1 ) {
            pc = pivotColumn( row );
        } else {
            pc = pivotColumn();
        }
        if ( pc != -1 )
            pr = pivotRow( pc );
        while ( pc != -1 && ( !b.contains( pc ) || row != -1 ) ) { // row != -1 new
            b.add( pc );
            if ( pr == -1 && !check() ) {
                // Impossible we return empty list
                return List.of();
            }
            if ( pr == -1 )
                break;
            pivot( pr, pc );
            fixMat( pr, pc );

            row = negativeb();
            if ( row != -1 ) {
                pc = pivotColumn( row );
            } else {
                pc = pivotColumn();
            }
            if ( pc != -1 )
                pr = pivotRow( pc );
        }

        // Check for INF value
        pc = pivotColumn();
        if ( pc != -1 && inf( pc ) ) {
            // Infinite
            if ( max )
                return List.of( Double.POSITIVE_INFINITY );
            return List.of( Double.NEGATIVE_INFINITY );
        }

        for ( int j = 0; j < numOfVar; ++j ) {
            int o = 0, oi = 0, c = 0;
            for ( int i = 0; i < rows; ++i )
                if ( mat.get( i ).get( j ) == 0 ) ;
                else if ( mat.get( i ).get( j ) == 1 ) {
                    ++o;
                    oi = i;
                } else
                    ++c;
            if ( o == 1 && c == 0 )
                solution.set( j, mat.get( oi ).get( cols - 1 ) );
            else {
                solution.set( j, 0.0 );
                if ( mat.get( rows - 1 ).get( j ) == 0 ) {
                    // Infinite Solutions
                    // Nothing for now
                }
            }
        }
        return solution;

//        if ( max )
//            sol.appendText( "Max is " + mat.get( rows - 1 ).get( cols - 1 ) + "\n" );
//        else
//            sol.appendText( "Min is " + -mat.get( rows - 1 ).get( cols - 1 ) + "\n" );

    }

    private static ArrayList<Double> parseEquation( String equ ) {
        ArrayList<Double> num = new ArrayList<>( cols );
        for ( int i = 0; i < cols; ++i )
            num.add( 0.0 );
        equ = equ.trim() + "+";
        int len = equ.length(), x = 0, j = 0, poi = 1;
        boolean pos = true, point = false;
        int i = 0;
        for ( ; i < len; ++i ) {
            switch ( equ.charAt( i ) ) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> {
                    x *= 10;
                    x += equ.charAt( i ) - '0';
                    if ( point )
                        poi *= 10;
                }
                case '+' -> {
                    if ( i == 0 ) {
                        pos = true;
                        break;
                    }
                    if ( !pos )
                        x = -x;
                    num.set( j++, x * 1.0 / poi );
                    if ( j == numOfVar )
                        j = cols - 1;
                    x = 0;
                    pos = true;
                    point = false;
                    poi = 1;
                }
                case '-' -> {
                    if ( i == 0 ) {
                        pos = false;
                        break;
                    }
                    if ( !pos )
                        x = -x;
                    num.set( j++, x * 1.0 / poi );
                    if ( j == numOfVar )
                        j = cols - 1;
                    x = 0;
                    pos = false;
                    point = false;
                    poi = 1;
                }
                case '.' -> point = true;
            }
        }
        return num;
    }
}
