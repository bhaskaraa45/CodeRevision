package com.android.aa45.coderevision.Adapters;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aa45.coderevision.AddProblemActivity;
import com.android.aa45.coderevision.Firebase.DataHolder;
import com.android.aa45.coderevision.MainActivity;
import com.android.aa45.coderevision.PrettifyHighlighter;
import com.android.aa45.coderevision.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {

    List<DataHolder> items ;
    Context context;
    private DatePickerDialog datePickerDialog;
    private  Button datePickerButton;
    private String selectedDate;
    int selectedDifficulty=-1;
    private final String[] diffItems = {"Basic", "Easy" , "Medium" , "Hard"};


    public recyclerViewAdapter(List<DataHolder> items,Context context) {
        this.items = items;
        this.context =context;
    }
    int redColor,blueColor,greenColor,whiteColor,orangeColor;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        redColor = parent.getContext().getResources().getColor(R.color.red);
        blueColor = parent.getContext().getResources().getColor(R.color.blue);
        greenColor = parent.getContext().getResources().getColor(R.color.green);
        whiteColor = parent.getContext().getResources().getColor(R.color.white);
        orangeColor = parent.getContext().getResources().getColor(R.color.orange);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataHolder dataHolder = items.get(position);
        holder.setTitle.setText(dataHolder.getTitle());
        holder.setDate.setText(dataHolder.getDate());
        holder.setSlNo.setText((position+1)+".");
        holder.setDiff.setText(dataHolder.getDifficulty());
        holder.setTopic.setText(dataHolder.getTag());
        holder.setTopic.setTextColor(blueColor);
        switch (dataHolder.getDifficulty()) {
            case "Hard":
                holder.setDiff.setTextColor(redColor);
                break;
            case "Easy":
            case "Medium":
                holder.setDiff.setTextColor(greenColor);
                break;
            default:
                holder.setDiff.setTextColor(whiteColor);
                break;
        }


        holder.rowCard.setOnClickListener(view -> {
            Dialog dialog = new Dialog(context,R.style.Base_Theme_CodeRevision);
            dialog.setContentView(R.layout.dialog_row_card);

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

            String summary_set = dataHolder.getSummary()==null? "" : dataHolder.getSummary();

            link.setText(dataHolder.getLink());
            slPlusTitle.setText((position+1) + ". "+dataHolder.getTitle());
            topic.setText("Topic : " + dataHolder.getTag());
            date.setText("Date : " + dataHolder.getDate());
            difficulty.setText("Difficulty : "+dataHolder.getDifficulty());
            summ.setText("Summary : \t"+summary_set);


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

                    backToMainDia.setOnClickListener(v1 -> {
                        codeDialog.dismiss();
                    });

                    String s = dataHolder.getCode().replace("\\n","\n");

                    PrettifyHighlighter highlighter = new PrettifyHighlighter();
                    String highlighted = highlighter.highlight("java",s);
                    code.setText(Html.fromHtml(highlighted));
                    codeDialog.show();

                    copy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("code",s);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog editDialog = new Dialog(context,R.style.Base_Theme_CodeRevision);
                    editDialog.setContentView(R.layout.dialog_edit_problem_details);

                    EditText code = editDialog.findViewById(R.id.code_edit);
                    EditText title = editDialog.findViewById(R.id.title_edit);
                    EditText link = editDialog.findViewById(R.id.link_edit);
                    EditText topic = editDialog.findViewById(R.id.topic_edit);
                    Button updateButton = editDialog.findViewById(R.id.update_button);
                    Spinner difficulty = editDialog.findViewById(R.id.spinner_edit);
                    datePickerButton = editDialog.findViewById(R.id.date_picker_edit);
                    ImageView back = editDialog.findViewById(R.id.back_edit);
                    EditText summ = editDialog.findViewById(R.id.summary_edit);


                    code.setText(dataHolder.getCode());
                    title.setText(dataHolder.getTitle());
                    link.setText(dataHolder.getLink());
                    topic.setText(dataHolder.getTag());
                    summ.setText(dataHolder.getSummary());

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
                            }
                            else if (diff <0 || updatedTopic.equals("") || updatedCode.equals("")) {
                                Toast.makeText(context, "Please fill the form appropriately", Toast.LENGTH_SHORT).show();
                            } else {

                                DataHolder updatedDataHolder = new DataHolder(updatedTitle,updatedLink,updatedDate,diffItems[diff],updatedTopic,updatedCode, dataHolder.getSlNo(), dataHolder.getTab(),updatedSummary);

                                updateData(updatedDataHolder);

                                dialog.dismiss();
                                editDialog.dismiss();
                            }

                        }
                    });

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setTitle = itemView.findViewById(R.id.setTitle);
            setSlNo = itemView.findViewById(R.id.setSlNo);
            setDate = itemView.findViewById(R.id.setDate);
            setTopic = itemView.findViewById(R.id.setTopic);
            setDiff = itemView.findViewById(R.id.setDiff);
            rowCard = itemView.findViewById(R.id.rowCard);
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


}
