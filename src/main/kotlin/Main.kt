fun main() {
    while (true) {
        print("Выберите задачу (1–5, 0 – выход): ")
        val choice = readLine()?.toIntOrNull() ?: -1

        when (choice) {
            0 -> {
                println("Программа завершена.")
                return
            }

            1 -> task1()
            2 -> task2()
            3 -> task3()
            4 -> task4()
            5 -> task5()
            else -> println("Нет такой задачи!")
        }
    }
}

fun task1() {
    println("Введите количество строк:")
    val rows = readLine()?.toIntOrNull()
    println("Введите количество столбцов:")
    val cols = readLine()?.toIntOrNull()

    if (rows == null || cols == null || rows <= 0 || cols <= 0) {
        println("Ошибка: неверные размеры массива.")
        return
    }

    val arr = Array(rows) { IntArray(cols) }
    val digitsUsed = mutableSetOf<Char>()

    println("Введите ${rows * cols} трёхзначных чисел (по одному в строке):")

    for (i in 0 until rows) {
        for (j in 0 until cols) {
            val num = readLine()?.toIntOrNull() ?: 0
            arr[i][j] = num
            digitsUsed.addAll(num.toString().filter { it.isDigit() }.toSet())
        }
    }

    println("Массив:")
    arr.forEach { row ->
        println(row.joinToString("\t"))
    }

    println("В массиве использовано ${digitsUsed.size} различных цифр")
}

fun task2() {
    val size = 5
    val arr = Array(size) { IntArray(size) }

    println("Введите ${size * size} чисел (матрица ${size}×${size}).")
    println("Можно вводить числа построчно или все сразу через пробел.")

    var inputCount = 0

    while (inputCount < size * size) {
        print("Осталось ввести ${size * size - inputCount} чисел: ")
        val line = readLine()?.trim()

        if (line.isNullOrEmpty()) {
            println("Пустая строка, попробуйте снова.")
            continue
        }

        val numbers = line.split(Regex("\\s+")).mapNotNull {
            val num = it.toIntOrNull()
            if (num == null) println(" '$it' не является числом — пропущено.")
            num
        }

        for (num in numbers) {
            if (inputCount >= size * size) break
            val row = inputCount / size
            val col = inputCount % size
            arr[row][col] = num
            inputCount++
        }

        println("Введено $inputCount из ${size * size} чисел.")
    }


    var symmetric = true
    for (i in 0 until size) {
        for (j in 0 until size) {
            if (arr[i][j] != arr[j][i]) {
                symmetric = false
                break
            }
        }
    }


    println("\nВведённый массив:")
    arr.forEach { row ->
        println(row.joinToString("\t"))
    }

    println("\nРезультат: ${if (symmetric) "Массив симметричен" else "Массив несимметричен"}")
}


fun task3() {
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯ"

    print("Введите режим (1 – зашифровать, 2 – расшифровать): ")
    val mode = readLine()?.toIntOrNull()

    print("Введите ключевое слово: ")
    val key = readLine()?.uppercase()?.filter { it in alphabet } ?: return

    print("Введите текст: ")
    val text = readLine()?.uppercase() ?: return

    val result = buildString {
        var k = 0
        for (ch in text) {
            if (ch !in alphabet) {
                append(ch)
                continue
            }
            val pos = alphabet.indexOf(ch)
            val shift = alphabet.indexOf(key[k % key.length])
            val newPos = if (mode == 1)
                (pos + shift) % alphabet.length
            else
                (pos - shift + alphabet.length) % alphabet.length
            append(alphabet[newPos])
            k++
        }
    }

    println("Результат: $result")
}

fun task4() {
    println("Введите первый массив через пробел:")
    val arr1 = readLine()?.split(Regex("\\s+"))?.mapNotNull { it.toIntOrNull() }?.toMutableList() ?: mutableListOf()
    println("Введите второй массив через пробел:")
    val arr2 = readLine()?.split(Regex("\\s+"))?.mapNotNull { it.toIntOrNull() }?.toMutableList() ?: mutableListOf()

    if (arr1.isEmpty() || arr2.isEmpty()) {
        println("Ошибка: один из массивов пуст.")
        return
    }

    val result = mutableListOf<Int>()
    for (num in arr1) {
        if (arr2.contains(num)) {
            result.add(num)
            arr2.remove(num)
        }
    }

    println("Пересечение: ${if (result.isEmpty()) "(пусто)" else result.joinToString(" ")}")
}

fun task5() {
    println("Введите слова через пробел:")
    val words = readLine()?.trim()?.split(Regex("\\s+")) ?: emptyList()

    if (words.isEmpty()) {
        println("Ошибка: нет слов для обработки.")
        return
    }

    val groups = words.groupBy { it.lowercase().toCharArray().sorted().joinToString("") }

    println("Группы слов:")
    groups.values.forEach { group ->
        println(group.joinToString(", "))
    }
}
