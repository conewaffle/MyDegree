package com.example.mydegree.Plan;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mydegree.R;

public class RenameFragment  extends DialogFragment {

    private EditText renamePlan;
    private Button renameConfirm;
    private int planId;
    private int planPosition;
    private String oldPlanName;
    private String newPlanName;
    private String progCode;
    public static final String FRAG_OLD_NAME = "fragOldName";
    public static final String FRAG_NEW_NAME = "fragNewName";
    public static final String FRAG_PLAN_ID = "fragPlanId";
    public static final String FRAG_PROG_CODE = "fragProgCode";
    public static final String FRAG_MAJOR = "fragMajor";
    public static final String FRAG_PLAN_SPINNER_POSITION = "fragPlanSpinnerPosition";


    public RenameFragment(){}

    public static RenameFragment newInstance(String oldPlanName, int planId, int planPosition, String progCode) {
        RenameFragment frag = new RenameFragment();
        Bundle args = new Bundle();
        args.putString(FRAG_OLD_NAME, oldPlanName);
        args.putInt(FRAG_PLAN_ID, planId);
        args.putInt(FRAG_PLAN_SPINNER_POSITION, planPosition);
        args.putString(FRAG_PROG_CODE, progCode);
        frag.setArguments(args);

        return frag;
    }

    public interface RenamePlanListener{
        void onFinishRename(Bundle bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rename_plan, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        renamePlan = view.findViewById(R.id.renamePlan);

        //region button set up onClick
        renameConfirm = view.findViewById(R.id.btnRename);
        renameConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlanName = renamePlan.getText().toString();
                if(newPlanName.equals(oldPlanName)){
                    dismiss();
                }
                Bundle toActivity = new Bundle();
                if(newPlanName.isEmpty()){
                    switch (progCode){
                        case "3584":
                            newPlanName = "Commerce / Information Systems";
                            break;
                        case "3964":
                            newPlanName = "Information Systems (Co-op) (Honours)";
                            break;
                        case "3979":
                            newPlanName = "Information Systems";
                            break;
                    }
                }
                toActivity.putInt(FRAG_PLAN_ID, planId);
                toActivity.putInt(FRAG_PLAN_SPINNER_POSITION, planPosition);
                toActivity.putString(FRAG_NEW_NAME, newPlanName);
                RenameFragment.RenamePlanListener listener = (RenamePlanListener) getActivity();
                listener.onFinishRename(toActivity);
                dismiss();
            }
        }) ;
        //endregion

        oldPlanName = getArguments().getString(FRAG_OLD_NAME);
        progCode = getArguments().getString(FRAG_PROG_CODE);
        planPosition = getArguments().getInt(FRAG_PLAN_SPINNER_POSITION);
        planId = getArguments().getInt(FRAG_PLAN_ID);

        renamePlan.setText(oldPlanName);






    }
}
