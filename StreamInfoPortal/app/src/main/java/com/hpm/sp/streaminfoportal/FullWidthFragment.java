package com.hpm.sp.streaminfoportal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FullWidthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FullWidthFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mUrl;
    @BindView(R.id.imageView)
    ImageView mImageView;


    public FullWidthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FullWidthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FullWidthFragment newInstance(String url) {
        FullWidthFragment fragment = new FullWidthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_PARAM1);
        }
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.full_width_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Picasso.with(getActivity())
                .load(mUrl)
                .resize(1080, 540)
                .centerInside()
                .into(mImageView);

        return rootView;
    }

}
