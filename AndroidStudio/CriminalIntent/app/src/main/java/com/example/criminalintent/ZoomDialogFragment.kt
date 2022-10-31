package com.example.criminalintent

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.DialogFragment

class ZoomDialogFragment : DialogFragment() {

    companion object {
        private const val PHOTO_URI = "PHOTO_URI"

        fun newInstance(photoFileName: String): ZoomDialogFragment {
            val frag = ZoomDialogFragment()
            val args = Bundle().apply {
                putSerializable(PHOTO_URI, photoFileName)
            }
            frag.arguments = args

            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.zoom_layout, container, false)
        val imageView = view.findViewById(R.id.zoom_image_view) as ImageView

        @Suppress("DEPRECATION")
        val photoFileName =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                arguments?.getSerializable(PHOTO_URI, String::class.java)!!
            else arguments?.getSerializable(PHOTO_URI) as String

        imageView.setImageBitmap(BitmapFactory.decodeFile(
            requireContext().filesDir.path + "/" + photoFileName))

        return view
    }
}
