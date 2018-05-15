package es.chipeit.android.activities

import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

import es.chipeit.android.R
import es.chipeit.android.io.ResourceReader
import es.chipeit.android.models.LibraryGame
import es.chipeit.android.ui.Fonts
import es.chipeit.android.ui.Intents
import es.chipeit.android.ui.gamelibrary.GameItemAdapter
import es.chipeit.android.ui.recyclerview.ItemClickListener

class GameLibraryActivity : AppCompatActivity() {
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var gameLibraryList: RecyclerView

    private lateinit var bottomSheetView: LinearLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamelibrary)

        initializeView()
    }

    override fun onResume() {
        super.onResume()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initializeView() {
        initializeToolbar()
        initializeBottomSheet()

        val games = LibraryGame.parse(
                ResourceReader.readRawResourceAsString(
                        this,
                        R.raw.index
                )
        )

        val actionButtonsClickListener = ActionButtonsClickListener(this)
        val sheetViewHolder = GameLibrarySheetViewHolder(
                findViewById(R.id.activity_gamelibrary_sheet)
        )

        Fonts.setTypeface(
                sheetViewHolder.titleTextView,
                assets,
                Fonts.PressStart2P
        )

        sheetViewHolder.playButton.setOnClickListener(actionButtonsClickListener)
        sheetViewHolder.restartButton.setOnClickListener(actionButtonsClickListener)
        sheetViewHolder.resumeButton.setOnClickListener(actionButtonsClickListener)

        val gameModelClickListener = GameModelClickListener(
                resources,
                sheetViewHolder,
                bottomSheetBehavior,
                actionButtonsClickListener
        )

        initializeGameLibraryRecyclerView(games, gameModelClickListener)
    }

    private fun initializeToolbar() {
        val titleTextView: TextView = findViewById(
                R.id.activity_gamelibrary_toolbar_title_txt
        )
        Fonts.Companion.setTypeface(titleTextView, assets, Fonts.PressStart2P)
        titleTextView.setTextColor(
                ResourcesCompat.getColor(
                        resources,
                        R.color.white,
                        null
                )
        )
    }

    private fun initializeBottomSheet() {
        val contentOverlay: View = findViewById(R.id.activity_gamelibrary_content_overlay)
        bottomSheetView = findViewById(R.id.activity_gamelibrary_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)
        bottomSheetBehavior.setBottomSheetCallback(BottomSheetCallback(contentOverlay))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        contentOverlay.setOnClickListener(ContentOverlayClickListener(bottomSheetBehavior))
    }

    private fun initializeGameLibraryRecyclerView(
            items: List<LibraryGame>,
            itemClickListener: ItemClickListener<LibraryGame>) {
        gameLibraryList = findViewById(R.id.activity_gamelibrary_list)
        viewManager = LinearLayoutManager(this)
        viewAdapter = GameItemAdapter(this, items, itemClickListener)

        gameLibraryList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        gameLibraryList.addOnScrollListener(
                GameLibraryScrollListener(bottomSheetBehavior)
        )
    }

    private class GameModelClickListener(
            private val resources: Resources,
            private val sheetViewHolder: GameLibrarySheetViewHolder,
            private val bottomSheetBehavior: BottomSheetBehavior<LinearLayout>,
            private val buttonsClickLister: ActionButtonsClickListener
    ) : ItemClickListener<LibraryGame> {
        override fun onItemClick(item: LibraryGame) {
            sheetViewHolder.backgroundDrawable.setColorFilter(
                    ResourcesCompat.getColor(
                            resources,
                            item.colorId,
                            null
                    ),
                    PorterDuff.Mode.SRC_ATOP
            )
            sheetViewHolder.titleTextView.text = item.title

            sheetViewHolder.restartButton.visibility =
                    if (item.hasSavedContent) View.VISIBLE
                    else View.GONE

            sheetViewHolder.resumeButton.visibility =
                    if (item.hasSavedContent) View.VISIBLE
                    else View.GONE

            sheetViewHolder.playButton.visibility =
                    if (item.hasSavedContent) View.GONE
                    else View.VISIBLE

            buttonsClickLister.game = item
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private class GameLibraryScrollListener(
            private val bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private class GameLibrarySheetViewHolder(sheet: View) {
        val backgroundDrawable: Drawable = sheet.background.current
        val titleTextView: TextView = sheet.findViewById(
                R.id.activity_gamelibrary_sheet_title_txt
        )
        val playButton: Button = sheet.findViewById(R.id.activity_gamelibrary_sheet_play_btn)
        val resumeButton: Button = sheet.findViewById(R.id.activity_gamelibrary_sheet_resume_btn)
        val restartButton: Button = sheet.findViewById(R.id.activity_gamelibrary_sheet_restart_btn)
    }

    private class ContentOverlayClickListener(
            private val bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    ) : View.OnClickListener {
        override fun onClick(v: View?) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private class BottomSheetCallback(
            private val contentOverlay: View) : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            contentOverlay.visibility = View.VISIBLE
            contentOverlay.alpha = 1 + slideOffset
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN)
                contentOverlay.visibility = View.GONE
        }
    }

    private class ActionButtonsClickListener(
            private val gameLibraryActivity: GameLibraryActivity
    ) : View.OnClickListener {
        var game: LibraryGame? = null

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.activity_gamelibrary_sheet_resume_btn ->
                    Intents.startEmulatorActivity(
                            gameLibraryActivity,
                            EmulatorActivity.Params(
                                    EmulatorActivity.Params.Action.RESUME,
                                    game
                            )
                    )

                R.id.activity_gamelibrary_sheet_play_btn,
                R.id.activity_gamelibrary_sheet_restart_btn ->
                    Intents.startEmulatorActivity(
                            gameLibraryActivity,
                            EmulatorActivity.Params(
                                    EmulatorActivity.Params.Action.PLAY,
                                    game
                            )
                    )
            }
        }
    }
}
