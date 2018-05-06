package es.chipeit.android.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.chipeit.android.R

class GameLibraryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_gamelibrary,
                container,
                false
        )

        return rootView
    }
}
