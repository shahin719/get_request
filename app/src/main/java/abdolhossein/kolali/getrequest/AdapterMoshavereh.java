package abdolhossein.kolali.getrequest;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterMoshavereh extends RecyclerView.Adapter<AdapterMoshavereh.MyViewHolder> {

    private List<Model> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public TextView name;
        public int id;

        public MyViewHolder(View view) {
            super(view);
            layout = (LinearLayout) view.findViewById(R.id.layout_item_legal);
            name = (TextView) view.findViewById(R.id.txtName);
        }
    }

    public AdapterMoshavereh(List<Model> legalList) {
        this.list = legalList;
    }

    @Override
    public AdapterMoshavereh.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_, parent, false);

        return new AdapterMoshavereh.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AdapterMoshavereh.MyViewHolder holder, final int position) {
        final Model model = list.get(position);
        holder.name.setText(model.getName());
        holder.id = model.getId();

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityTextMoshavereh.class);
                intent.putExtra("ID",model.getId());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
