import java.util.Random;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ArraySumForkJoin {

    // Рекурсивна задача для обчислення суми елементів масиву
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 20;  // Поріг для розбиття
        private int[] array;
        private int start, end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            // Якщо кількість елементів менша або рівна порогу, обчислюємо суму
            if (end - start <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Розбиваємо завдання на дві частини
                int middle = (start + end) / 2;
                SumTask leftTask = new SumTask(array, start, middle);
                SumTask rightTask = new SumTask(array, middle, end);

                // Запускаємо обидва підзавдання паралельно
                leftTask.fork();
                rightTask.fork();

                // Обчислюємо результат
                long leftResult = leftTask.join();
                long rightResult = rightTask.join();

                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        // Ініціалізація масиву
        int size = 1_000_000;
        int[] array = new int[size];
        Random rand = new Random();

        // Заповнюємо масив випадковими числами в діапазоні від 0 до 100
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(101); // Випадкове число від 0 до 100
        }

        // Створення ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();

        // Створення і запуск основної рекурсивної задачі
        SumTask task = new SumTask(array, 0, size);
        long result = pool.invoke(task);

        // Виведення результату
        System.out.println("Сума всіх елементів масиву: " + result);
    }
}
