package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.utility.ProgressDialogDisplayUtility;

/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 */

public class ReportsFrag extends Fragment {
    private ProgressDialogDisplayUtility mDialog;
    private CardView mCardPayslip, mCardAttendance, mCardInvoice;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.reports, container, false);
        mDialog = new ProgressDialogDisplayUtility(getActivity());
        //initialze the id's
        mCardPayslip = (CardView) view.findViewById(R.id.card_payslip_report);
        mCardAttendance = (CardView) view.findViewById(R.id.card_attendance_report);
        mCardInvoice = (CardView) view.findViewById(R.id.card_invoice_report);

        //get  the msg from string resourse file
        final String prgMsg = getResources().getString(R.string.progressDilogMsg);
        //go to payslip class
        mCardPayslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.showDialog(prgMsg);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new SalaryPayslipFrag(mDialog)).commit();
            }
        });

        //go to attendance report class
        mCardAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.showDialog(prgMsg);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new AttendanceReportFrag(mDialog)).commit();
            }
        });

        //go to invoice report class
        mCardInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.showDialog(prgMsg);
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new InvoiceReportFrag(mDialog)).commit();
            }
        });
        return view;
    }
}
