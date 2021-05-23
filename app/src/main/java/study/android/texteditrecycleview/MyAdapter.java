package study.android.texteditrecycleview;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<String> terms;

    MyAdapter(){
        terms = new ArrayList();
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        // pass MyCustomEditTextListener to viewholder in onCreateViewHolder
        // so that we don't have to do this expensive allocation in onBindViewHolder
        ViewHolder vh = new ViewHolder(v, new MyCustomEditTextListener());

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in mDataset to update
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.mEditText.setText(terms.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public EditText mEditText;
        public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
            super(v);

            this.mEditText = (EditText) v.findViewById(R.id.editText);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mEditText.addTextChangedListener(myCustomEditTextListener);
        }
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            terms.set(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}