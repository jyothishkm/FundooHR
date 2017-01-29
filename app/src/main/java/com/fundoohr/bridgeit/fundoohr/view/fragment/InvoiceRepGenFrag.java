package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fundoohr.bridgeit.fundoohr.R;

/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 */
public class InvoiceRepGenFrag extends Fragment {
    private ImageView mImageBack;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.invoice_rep_gen, container, false);
        mImageBack = (ImageView) view.findViewById(R.id.image_back_invoice_gen);

        //back to invoice report
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new InvoiceReportFrag()).addToBackStack("").commit();
            }
        });
        return view;
    }
}
