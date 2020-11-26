package com.outlook.bselzer1.chess.ui.screen.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.outlook.bselzer1.chess.game.ai.Difficulty
import com.outlook.bselzer1.chess.game.board.BoardName
import com.outlook.bselzer1.chess.ui.gdx.GdxCompanion
import com.outlook.bselzer1.chess.ui.gdx.actor.GdxTextButton
import com.outlook.bselzer1.chess.ui.screen.game.GameScreen

/**
 * The button for selecting the associated difficulty enum.
 */
class DifficultyButton(difficulty: Difficulty) : GdxTextButton(difficulty.toString())
{
    init
    {
        addListener(object : ChangeListener()
        {
            override fun changed(event: ChangeEvent, actor: Actor)
            {
                //TODO set difficulty
                //TODO board selection
                GdxCompanion.GAME.screen = GameScreen(BoardName.WESTERN)
            }
        })
    }
}