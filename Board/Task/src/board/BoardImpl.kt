package board

import board.Direction.*
import java.lang.IllegalArgumentException

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    private val cells = (1..width).flatMap { i ->
        (1..width).map { j ->
            Cell(i, j)
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return getAllCells().firstOrNull { cell -> cell == Cell(i, j) }
    }

    override fun getCell(i: Int, j: Int): Cell {
        return getCellOrNull(i, j) ?: throw IllegalArgumentException()
    }

    override fun getAllCells(): Collection<Cell> {
        return cells
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        return jRange.mapNotNull { j -> getCellOrNull(i, j) }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        return iRange.mapNotNull { i -> getCellOrNull(i, j) }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            UP -> getCellOrNull(i - 1, j)
            DOWN -> getCellOrNull(i + 1, j)
            RIGHT -> getCellOrNull(i, j + 1)
            LEFT -> getCellOrNull(i, j - 1)
        }

    }
}

fun createSquareBoard(width: Int): SquareBoard {
    return SquareBoardImpl(width)
}

open class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {

    private val board = getAllCells().map { it to null }.toMap<Cell, T?>().toMutableMap()

    override fun get(cell: Cell): T? {
        return board[cell]
    }

    override fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return board.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return board.entries.firstOrNull { predicate(it.value) }?.key
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return board.filterValues(predicate).isNotEmpty()
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return board.entries.all { predicate(it.value) }
    }
}

fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)



