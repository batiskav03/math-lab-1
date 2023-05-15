import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        int sign = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println(">> (+) ввод с клавиатуры, (-) ввод с файла");
        float[][] matrix;
        if (scan.next().equals("+")) {
            matrix = inputData();
        } else {
            matrix = inputData("input/input-matrix");
        }

        gauss(matrix, sign);

    }

    //расширенная матрица
    public static float[][] inputData() {
        Scanner scan = new Scanner(System.in);
        System.out.println(">> Введите размерность матрицы: ");
        int size = scan.nextInt();
        float[][] matrix = new float[size][];
        for (int i = 0; i < size; i++) {
            System.out.println(">> Введите " + (i + 1) + " строку матрицы:");
            matrix[i] = new float[size + 1];
            for (int j = 0; j <= size; j++) {
                matrix[i][j] = scan.nextFloat();
            }
        }
        return matrix;
    }

    public static float[][] inputData(String path) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(path));
        int size = scan.nextInt();
        float[][] matrix = new float[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new float[size + 1];
            for (int j = 0; j <= size; j++) {
                matrix[i][j] = scan.nextFloat();
            }
        }
        return matrix;
    }

    //вычисление определителя треугольной матрицы по главной диагонале
    public static float determinantTriangle(float[][] matrix, int sign) {
        float determinant = 1;
        for (int i = 0; i < matrix.length; i++) {
            determinant *= matrix[i][i] * Math.pow(-1, sign) ;
        }
        return determinant;
    }





        public static void gauss(float[][] matrix, int sign) {
            // прямой ход
            for (int i = 0; i < matrix.length - 1; i++) {
                //главный элемент по умолчанию
                int max = i;
                //поиск альтернативного главного элемента
                for (int m = i + 1; m < matrix.length; m++) {
                    if (matrix[m][i] > matrix[max][i]) {
                        max = m;
                    }
                }
                //в случае если главный элемент сменился - меняем местами строчки
                //так чтобы главный элемент стоял на главной диагонале
                if (max != i) {
                    sign += 1;
                    for (int j = 0; j <= matrix.length; j++) {
                        float c = matrix[i][j];
                        matrix[i][j] = matrix[max][j];
                        matrix[max][j] = c;
                    }
                }
                //приводим матрицу к треугольному виду
                for (int k = i + 1; k < matrix.length; k++ ) {
                    float multiplier = matrix[k][i] / matrix[i][i];
                    for (int j = i; j <= matrix.length; j++) {
                        matrix[k][j] -= multiplier * matrix[i][j];
                    }
                }
                System.out.println("Кол-во перестановок: ");
                System.out.println(sign);



            }
            System.out.println("Вывод преобразованной матрицы:");
            printMatrix(matrix);

            // нахождение решений СЛАУ (обратный ход)
            float[] solutions = new float[matrix.length];
            for (int i = matrix.length - 1; i >= 0; i--) {
                float sum = 0;
                for (int j = i + 1; j < matrix.length; j++) {
                    sum += matrix[i][j] * solutions[j];
                }
                solutions[i] = (matrix[i][matrix.length] - sum) / matrix[i][i];
            }
            System.out.println("Вектор неизвестных: ");
            for (float num : solutions) {
                System.out.print(" " + num + ";");
            }

            System.out.println("\nОпределитель:");
            System.out.println(determinantTriangle(matrix, sign));

            // вычисление невязок
            float[] delta = new float[matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                float sum = 0;
                for (int j = 0; j < matrix.length; j++) {
                    sum += matrix[i][j] * solutions[j];
                }
                // разница между левой и правой частью уравнения
                delta[i] = sum - matrix[i][matrix.length];
            }

            System.out.println("Вектор невязок: ");
            for (float num : delta) {
                System.out.printf("%.150f\n", num );
            }
        }


        public static void printMatrix(float[][] matrix) {
            System.out.println("---- ---- ---- ----");
            for (float[] line : matrix) {
                for (float e : line) {
                    System.out.printf("%.2f\t\t", e);
                }
                System.out.println();
            }
            System.out.println("---- ---- ---- ----");
        }


}
