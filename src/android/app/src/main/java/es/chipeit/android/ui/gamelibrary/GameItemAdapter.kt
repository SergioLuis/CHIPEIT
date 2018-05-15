package es.chipeit.android.ui.gamelibrary

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import es.chipeit.android.R
import es.chipeit.android.models.LibraryGame
import es.chipeit.android.ui.Fonts
import es.chipeit.android.ui.recyclerview.ItemClickListener

class GameItemAdapter(
        context: Context,
        private val dataSet: List<LibraryGame>,
        private val itemClickListener: ItemClickListener<LibraryGame>) :
        RecyclerView.Adapter<GameItemAdapter.ViewHolder>() {
    private val pressStartTypeface = Fonts.createTypeface(context.assets, Fonts.PressStart2P)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                pressStartTypeface,
                LayoutInflater.from(parent.context).inflate(
                        R.layout.list_gamelibrary_item,
                        parent,
                        false
                ) as View
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], itemClickListener)
    }

    class ViewHolder(val typeface: Typeface?, val view: View): RecyclerView.ViewHolder(view) {
        private val smallTitleTextView: TextView = view.findViewById(
                R.id.list_gamelibrary_item_foregroundtitle
        )

        private val bigTitleTextView: TextView = view.findViewById(
                R.id.list_gamelibrary_item_backgroundtitle
        )

        private val titleBackground: LinearLayout = view.findViewById(
                R.id.list_gamelibrary_item_foregroundtitle_background
        )

        init {
            smallTitleTextView.typeface = typeface
            bigTitleTextView.typeface = typeface
        }

        fun bind(libraryGame: LibraryGame, clickListener: ItemClickListener<LibraryGame>) {
            smallTitleTextView.text = libraryGame.title
            bigTitleTextView.text = libraryGame.title
            titleBackground.setBackgroundColor(
                    ResourcesCompat.getColor(
                            view.resources,
                            libraryGame.colorId,
                            null
                    )
            )

            view.setOnClickListener {
                clickListener.onItemClick(libraryGame)
            }
        }
    }
}
