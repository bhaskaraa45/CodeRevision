package com.bhaskar.aa45.coderevision.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhaskar.aa45.coderevision.Firebase.DataHolder;
import com.bhaskar.aa45.coderevision.MeFragment;
import com.bhaskar.aa45.coderevision.PrettifyHighlighter;
import com.bhaskar.aa45.coderevision.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    List<DataHolder> items ;
    Context context;
    private DatePickerDialog datePickerDialog;
    private  Button datePickerButton;
    private String selectedDate;
    int selectedDifficulty=-1;
    private final String[] diffItems = {"Basic", "Easy" , "Medium" , "Hard"};
    private String questionTag="";


    public recyclerViewAdapter(List<DataHolder> items,Context context) {
        this.items = items;
        this.context =context;
    }
    int redColor,blueColor,greenColor,whiteColor,orangeColor,blackColor,primaryColor;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        redColor = parent.getContext().getResources().getColor(R.color.red);
        blueColor = parent.getContext().getResources().getColor(R.color.blue_adapter);
        greenColor = parent.getContext().getResources().getColor(R.color.green);
        whiteColor = parent.getContext().getResources().getColor(R.color.white);
        orangeColor = parent.getContext().getResources().getColor(R.color.orange);
        blackColor = parent.getContext().getResources().getColor(R.color.black);
        primaryColor = parent.getContext().getResources().getColor(R.color.primary);
        return new ViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataHolder dataHolder = items.get(position);
        holder.setTitle.setText(dataHolder.getTitle());
        holder.setDate.setText(dataHolder.getDate());
        holder.setSlNo.setText((position+1)+".");
        holder.setDiff.setText(dataHolder.getDifficulty());
        holder.setTopic.setText(dataHolder.getTag());
        holder.setTopic.setTextColor(blueColor);
        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add(holder.setDate);
        textViews.add(holder.setDiff);
        textViews.add(holder.setTitle);
        textViews.add(holder.setSlNo);
        textViews.add(holder.topics);

        if(MeFragment.check==-1 && AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES) {
            holder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.gradient_color));
        }

        if(MeFragment.check!=-1) {
            uiModeChange(textViews, MeFragment.isDark);
            if (MeFragment.isDark) {
                holder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.gradient_color));
            }
            else
                holder.cardLayout.setBackground(context.getResources().getDrawable(R.drawable.gradient_color_light));
        }

        switch (dataHolder.getDifficulty()) {
            case "Hard":
                holder.setDiff.setTextColor(redColor);
                break;
            case "Easy":
                holder.setDiff.setTextColor(greenColor);
                break;
            case "Medium":
                holder.setDiff.setTextColor(orangeColor);
                break;
        }


        holder.rowCard.setOnClickListener(view -> {
            Dialog dialog = new Dialog(context,R.style.Base_Theme_CodeRevision);
            dialog.setContentView(R.layout.dialog_row_card);

            TextView textView1 = dialog.findViewById(R.id.text31);
            TextView textView2 = dialog.findViewById(R.id.text32);
            TextView textView3 = dialog.findViewById(R.id.text33);
            TextView textView4 = dialog.findViewById(R.id.text34);
            TextView textView5 = dialog.findViewById(R.id.text35);
            TextView editText = dialog.findViewById(R.id.editText);
            TextView deleteText = dialog.findViewById(R.id.deleteText);

            TextView slPlusTitle = dialog.findViewById(R.id.sl_plus_title);
            TextView topic = dialog.findViewById(R.id.topic);
            TextView difficulty = dialog.findViewById(R.id.difficulty);
            TextView date = dialog.findViewById(R.id.date);
            TextView link = dialog.findViewById(R.id.link);
            TextView codeBtn = dialog.findViewById(R.id.codeBtn);
            RelativeLayout edit = dialog.findViewById(R.id.edit);
            RelativeLayout delete = dialog.findViewById(R.id.delete);
            ImageView back = dialog.findViewById(R.id.back);
            TextView summ = dialog.findViewById(R.id.summary);
            TextView status = dialog.findViewById(R.id.status);
            LinearLayout parent_details = dialog.findViewById(R.id.parent_dialog_details);

            String summary_set = dataHolder.getSummary()==null? "" : dataHolder.getSummary();

            //for changing ui
            ArrayList<TextView> texts = new ArrayList<>();
            texts.add(textView1);
            texts.add(textView2);
            texts.add(textView3);
            texts.add(textView4);
            texts.add(textView5);
            texts.add(date);
            texts.add(slPlusTitle);
            texts.add(topic);
            texts.add(editText);
            texts.add(deleteText);

            link.setText(dataHolder.getLink());
            slPlusTitle.setText((position+1) + ". "+dataHolder.getTitle());
            topic.setText(dataHolder.getTag());
            date.setText("Date : " + dataHolder.getDate());
            difficulty.setText(dataHolder.getDifficulty());
            summ.setText("Summary : \t"+summary_set);
            status.setText(dataHolder.getTab());

            switch (dataHolder.getTab()){
            case "Solved":
                status.setTextColor(greenColor);
                break;
            case "Tried":
                status.setTextColor(redColor);
                break;
            default:
                status.setTextColor(orangeColor);
        }
            switch (dataHolder.getDifficulty()) {
                case "Hard":
                    difficulty.setTextColor(redColor);
                    break;
                case "Easy":
                    difficulty.setTextColor(greenColor);
                    break;
                case "Medium":
                    difficulty.setTextColor(orangeColor);
                    break;
                default:
                    difficulty.setTextColor(whiteColor);
                    break;
            }

            //if light mode
            if(MeFragment.check==-1 && AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO){
                back.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow_black));
                edit.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                delete.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
            }
            if(MeFragment.check!=-1){
                uiModeChange(texts,MeFragment.isDark);
                if(MeFragment.isDark){
                    parent_details.setBackgroundColor(primaryColor);
                    back.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow));
                    edit.setBackground(context.getResources().getDrawable(R.drawable.bg_me_items));
                    delete.setBackground(context.getResources().getDrawable(R.drawable.bg_me_items));
                }else{
                    parent_details.setBackgroundColor(whiteColor);
                    back.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow_black));
                    edit.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                    delete.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                }
            }


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url;
                    if (!dataHolder.getLink().startsWith("http://") && !dataHolder.getLink().startsWith("https://")) {
                        url = "http://" + dataHolder.getLink();
                    }else{
                        url = dataHolder.getLink();
                    }
                    Intent problemLink = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(problemLink);
                }
            });
            codeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog codeDialog = new Dialog(context,R.style.Base_Theme_CodeRevision);
                    codeDialog.setContentView(R.layout.code_view);

                    TextView code = codeDialog.findViewById(R.id.code);
                    ImageView backToMainDia = codeDialog.findViewById(R.id.back_to_dialog);
                    ImageView copy = codeDialog.findViewById(R.id.copy);
                    LinearLayout parent = codeDialog.findViewById(R.id.parent_layout_code);
                    CheckBox syntax = codeDialog.findViewById(R.id.syntax_highlight);

                    syntax.setChecked(true);

                    if((MeFragment.check==-1 && AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO) || MeFragment.check==0) {
                        parent.setBackgroundColor(context.getResources().getColor(R.color.grey_bg));
                        backToMainDia.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow_black));
                        syntax.setTextColor(blackColor);
                    }
                    else{
                        syntax.setTextColor(whiteColor);
                        parent.setBackgroundColor(primaryColor);
                        backToMainDia.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow));
                    }

                    backToMainDia.setOnClickListener(v1 -> {
                        codeDialog.dismiss();
                    });

                    String s = dataHolder.getCode().replace("\\n","\n");
                    String nk = s.replace("  ","\t");
                    PrettifyHighlighter highlighter = new PrettifyHighlighter();
                    String highlighted = highlighter.highlight("java",nk);
                    String temp = highlighted.replace(">><",">&gt<");
                    String temp2 = temp.replace("><<",">&lt<");
                    String str = temp2.replace("\n","<br/>");
                    code.setText(Html.fromHtml(str));

                    syntax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                code.setText(Html.fromHtml(str));
                            }else{
                                code.setTextColor(whiteColor);
                                code.setText(s);
                            }
                        }
                    });


                    codeDialog.show();

                    copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("code",s);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(context, "Code Copied", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String[] topic_list = context.getResources().getStringArray(R.array.topic_list);

                    Dialog editDialog = new Dialog(context,R.style.Base_Theme_CodeRevision);
                    editDialog.setContentView(R.layout.dialog_edit_problem_details);

                    EditText code = editDialog.findViewById(R.id.code_edit);
                    EditText title = editDialog.findViewById(R.id.title_edit);
                    EditText link = editDialog.findViewById(R.id.link_edit);
                    TextView topic = editDialog.findViewById(R.id.topic_edit);
                    Button updateButton = editDialog.findViewById(R.id.update_button);
                    Spinner difficulty = editDialog.findViewById(R.id.spinner_edit);
                    datePickerButton = editDialog.findViewById(R.id.date_picker_edit);
                    ImageView back = editDialog.findViewById(R.id.back_edit);
                    EditText summ = editDialog.findViewById(R.id.summary_edit);
                    LinearLayout addTag = editDialog.findViewById(R.id.add_tag_edit);


                    code.setText(dataHolder.getCode());
                    title.setText(dataHolder.getTitle());
                    link.setText(dataHolder.getLink());
                    topic.setText(dataHolder.getTag());
                    summ.setText(dataHolder.getSummary());
                    if(dataHolder.getTab().equals("Wishlist")){
                        code.setHint("Code (Optional)");
                        summ.setHint("Write Summary of the Code (Optional)");
                    }

                    String[] prevTags = dataHolder.getTag().split("    ",0);

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editDialog.dismiss();
                        }
                    });

                    //datePicker
                    initDatePicker();
                    selectedDate = dataHolder.getDate();
                    datePickerButton.setText(selectedDate);
                    datePickerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            datePickerDialog.show();
                        }
                    });

                    //difficulty level
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                            R.array.difficulty_items, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    difficulty.setAdapter(adapter);

                    difficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                            selectedDifficulty = pos - 1;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    switch (dataHolder.getDifficulty()){
                        case "Hard" :
                            difficulty.setSelection(4);
                            break;
                        case "Medium" :
                            difficulty.setSelection(3);
                            break;
                        case "Easy" :
                            difficulty.setSelection(2);
                            break;
                        case "Basic" :
                            difficulty.setSelection(1);
                            break;
                        default:
                            difficulty.setSelection(0);
                    }

                    ArrayList<String> list = new ArrayList<>(Arrays.asList(topic_list));

                    boolean[] listCheck = new boolean[topic_list.length];
                    Arrays.fill(listCheck,false);

                    for(String str : prevTags){
                        if(!str.equals("")){
                            int index = list.indexOf(str);
                            if(index>=0 && index < listCheck.length) {
                                listCheck[index] = true;
                            }
                        }
                    }


                    addTag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Select Tags/Topics :")
                                    .setMultiChoiceItems(topic_list, listCheck, new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                            if(isChecked){
                                                listCheck[which] = true;
                                            }
                                        }
                                    });
                            builder.setCancelable(false);
                            builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    questionTag ="";
                                    for(int i=0 ;i<listCheck.length;i++){
                                        if(listCheck[i]){
                                            questionTag += topic_list[i] + "    ";
                                        }
                                    }
                                    topic.setText(questionTag);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                    });



                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String updatedTitle = title.getText().toString();
                            String updatedTopic = topic.getText().toString();
                            String updatedLink = link.getText().toString();
                            String updatedCode = code.getText().toString();
                            String updatedDate = selectedDate;
                            String updatedSummary = summ.getText().toString();

                            int diff = selectedDifficulty;

                            if(!Patterns.WEB_URL.matcher(updatedLink).matches()){
                                Toast.makeText(context, "Please Enter valid Link", Toast.LENGTH_SHORT).show();
                            }else if(!dataHolder.getTab().equals("Wishlist") && updatedCode.equals("")){
                                Toast.makeText(context, "Please fill the form appropriately", Toast.LENGTH_SHORT).show();
                            }
                            else if (diff <0 || updatedTopic.equals("")) {
                                Toast.makeText(context, "Please fill the form appropriately", Toast.LENGTH_SHORT).show();
                            } else {
                                DataHolder updatedDataHolder = new DataHolder(updatedTitle,updatedLink,updatedDate,diffItems[diff],updatedTopic,updatedCode, dataHolder.getSlNo(), dataHolder.getTab(),updatedSummary);
                                updateData(updatedDataHolder);

                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                editDialog.dismiss();
                            }

                        }
                    });

                    ImageView calendar_icon = editDialog.findViewById(R.id.calendar_icon_edit);
                    ImageView link_icon = editDialog.findViewById(R.id.link_icon_edit);
                    ImageView tag_icon = editDialog.findViewById(R.id.tag_icon_edit);
                    ImageView code_icon = editDialog.findViewById(R.id.code_icon_edit);
                    ImageView summary_icon = editDialog.findViewById(R.id.summary_icon_edit);
                    ImageView diff_icon = editDialog.findViewById(R.id.diff_icon_edit);

                    TextView edit_headline = editDialog.findViewById(R.id.edit_headline);
                    LinearLayout parent_edit = editDialog.findViewById(R.id.parent_edit_details);

                    RelativeLayout diff_layout = editDialog.findViewById(R.id.spinner_layout_edit);
                    RelativeLayout date_layout = editDialog.findViewById(R.id.date_layout_edit);
                    RelativeLayout title_layout = editDialog.findViewById(R.id.enter_title_edit);
                    RelativeLayout link_layout = editDialog.findViewById(R.id.enter_link_edit);
                    RelativeLayout tag_layout = editDialog.findViewById(R.id.enter_tag_edit);
                    RelativeLayout code_layout = editDialog.findViewById(R.id.enter_code_edit);
                    RelativeLayout summary_layout = editDialog.findViewById(R.id.enter_summary_edit);
                    if((MeFragment.check==-1 && AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO) || (MeFragment.check==0)){
                        parent_edit.setBackgroundColor(whiteColor);
                        calendar_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.edit_cal_dark));
                        link_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.link_dark));
                        tag_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.tag_dark));
                        code_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.code_dark));
                        summary_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.summary_dark));
                        diff_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.difficulty_dark));
                        ImageView img = editDialog.findViewById(R.id.image_add_circle_edit);
                        img.setImageDrawable(context.getResources().getDrawable(R.drawable.add_circle_dark));

                        diff_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        date_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        title_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        link_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        tag_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        code_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        summary_layout.setBackground(context.getResources().getDrawable(R.drawable.edittet_shape));
                        datePickerButton.setTextColor(blackColor);
                        back.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow_black));
                        edit_headline.setTextColor(blackColor);
                        code.setTextColor(blackColor);
                        title.setTextColor(blackColor);
                        link.setTextColor(blackColor);
                        topic.setTextColor(blackColor);
                        summ.setTextColor(blackColor);
                    }
                    if(MeFragment.check!=-1){
                        if(MeFragment.isDark){
                            parent_edit.setBackgroundColor(primaryColor);
                            calendar_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_edit_calendar_24));
                            link_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.link));
                            tag_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.tag));
                            code_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.code_icon));
                            summary_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.baseline_summarize_24));
                            diff_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.difficulty_level));
                            ImageView img = editDialog.findViewById(R.id.image_add_circle_edit);
                            img.setImageDrawable(context.getResources().getDrawable(R.drawable.add_circle_light));

                            diff_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            date_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            title_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            link_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            tag_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            code_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            summary_layout.setBackground(context.getResources().getDrawable(R.drawable.edit_form_shape));
                            datePickerButton.setTextColor(whiteColor);
                            back.setImageDrawable(context.getResources().getDrawable(R.drawable.back_arrow));
                            edit_headline.setTextColor(whiteColor);
                            code.setTextColor(whiteColor);
                            title.setTextColor(whiteColor);
                            link.setTextColor(whiteColor);
                            topic.setTextColor(whiteColor);
                            summ.setTextColor(whiteColor);
                        }
                    }


                    editDialog.show();

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert(dataHolder,dialog);
                }
            });


            dialog.show();

        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView setTitle;
        public TextView setSlNo;
        public TextView setDate;
        public TextView setTopic;
        public TextView setDiff;
        public CardView rowCard;
        public RelativeLayout cardLayout;
        public TextView topics;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setTitle = itemView.findViewById(R.id.setTitle);
            setSlNo = itemView.findViewById(R.id.setSlNo);
            setDate = itemView.findViewById(R.id.setDate);
            setTopic = itemView.findViewById(R.id.setTopic);
            setDiff = itemView.findViewById(R.id.setDiff);
            rowCard = itemView.findViewById(R.id.rowCard);
            cardLayout = itemView.findViewById(R.id.card_layout);
            topics = itemView.findViewById(R.id.text22);
        }
    }


    //for date picker
    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                selectedDate = makeDateString(day,month +1 , year);
                datePickerButton.setText(selectedDate);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(context, style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        return (day + "-" + month + "-" + year);
    }

    //update data on firebase rtdb
    private void updateData(DataHolder dataHolder){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = db.getReference(); //root
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference finalRef = myRef.child("user").child(uid).child(dataHolder.getTab()).child(dataHolder.getSlNo()); //root->user->uid->branch(tab)->Sl

        //update data
        finalRef.setValue(dataHolder);
    }
    private void deleteData(DataHolder dataHolder){
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://code-revision-default-rtdb.asia-southeast1.firebasedatabase.app/");
        DatabaseReference myRef = db.getReference(); //root
        String uid = FirebaseAuth.getInstance().getUid();
        //root->user->uid->branch(tab)->Sl(unique ID)
        DatabaseReference finalRef = myRef.child("user").child(uid).child(dataHolder.getTab()).child(dataHolder.getSlNo());

        //delete data
        finalRef.setValue(null);
    }
    private void showAlert(DataHolder dataHolder,Dialog dialog) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete this permanently?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
                deleteData(dataHolder);
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void uiModeChange(ArrayList<TextView> texts, boolean dark){
        for(TextView text : texts){
            if(!dark){
                text.setTextColor(blackColor);
            }else{
                text.setTextColor(whiteColor);
            }
        }

    }



}
