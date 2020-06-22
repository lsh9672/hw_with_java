package com.example.winter_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class ManagementFragment extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager; // recyclerview는 layoutmanager를 연결해줘야됨
    private ArrayList<User> arrayList;
    private ArrayList<String> unique_List;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private Spinner year_spinner,month_spinner;

   private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.management_fragment,container,false);
        auth = FirebaseAuth.getInstance();

        year_spinner = (Spinner)view.findViewById(R.id.year_spinner);
        month_spinner = (Spinner)view.findViewById(R.id.month_spinner);

        recyclerView= view.findViewById(R.id.recyclerview);// 아이디 연결
        recyclerView.setHasFixedSize(true);//리사이클러뷰의 성능강화
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//User객체를 담을 어레이 리스트(어댑터 쪽으로)
        unique_List= new ArrayList<>();
        adapter= new CustomAdapter(arrayList,getActivity());
        recyclerView.setAdapter(adapter);//리사이클러뷰의 어댑터 연결
        database= FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동



        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("test","test");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("test","test");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseReference= database.getReference(auth.getCurrentUser().getUid());//DB테이블 연결
        databaseReference.addValueEventListener(new ValueEventListener() {//DB에서 수정하면 바로 새로고침됨
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 DB의 데이터를 받아오는 곳
                arrayList.clear();//기존배열리스트가 존재하지 않게 초기화하는 것(방지차원에서)
                unique_List.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){//반복문으로 데이터 리스트를 추출해냄- 처음부터 끝까지 다 읽어냄
                    User user= snapshot.getValue(User.class);//만들어뒀던 User객체에 데이터를 담는다-핵심
                    String unique_id = snapshot.getKey();
                    arrayList.add(user);//담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                    unique_List.add(unique_id);
                }
                adapter.notifyDataSetChanged();//리스트 저장및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //디비를 가져오던중 에러 발생시
                Log.e("ManagemetFragmnet", String.valueOf(databaseError.toException()));//에러문 출력


            }
        });

        return view;
    }

    //어댑터
    public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<User> arrayList; //User파일에 정보를 담는 리스트를 만듦
        private Context context;//액티비티마다 콘텍스트 존재, 어댑터에서 액티비티 액션들을 가져올때 필요함(어댑터에는 없기때문에 선언)
        FirebaseStorage storage=FirebaseStorage.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        public CustomAdapter(ArrayList<User> arrayList, FragmentActivity activity) {
            this.arrayList = arrayList;
            this.context = activity;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//어댑터가 리스트뷰에 연결되고 나서 최초로 뷰홀더를 만들어냄
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {//각 아이템들에 대해 매칭
            Glide.with(holder.itemView)
                    .load(arrayList.get(position).getImageUrl())
                    .into(((CustomViewHolder)holder).list_picture); // 이미지를 가져오기 위해서 Glide 라이브러리를 사용
            ((CustomViewHolder)holder).list_time.setText(arrayList.get(position).getCapture_time());
            ((CustomViewHolder)holder).list_car.setText(arrayList.get(position).getCar());
            ((CustomViewHolder)holder).btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_content(position);
                }
            });

        }

        @Override
        public int getItemCount() {
            //삼항연산자
            return (arrayList !=null ? arrayList.size() :0);
        }
        public void delete_content(final int position){
            storage.getReference().child("images").child(arrayList.get(position).getImage_delete_name()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    database.getReference().child(auth.getCurrentUser().getUid()).child(unique_List.get(position)).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {//디비에서는 거의 실패할일이 없어서 쓰지 않음
                        @Override
                        public void onSuccess(Void aVoid) {
                            //arrayList.remove(position);
                            //notifyItemRemoved(position);
                            Toast.makeText(getActivity(), "데이터 삭제 성공", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView list_picture;
            TextView list_time;
            TextView list_car;
            ImageView btn_delete;
            public CustomViewHolder(@NonNull View itemView) {//액티비티에서 하듯이 하면됨
                super(itemView);
                this.list_picture = itemView.findViewById(R.id.list_picture);
                this.list_time = itemView.findViewById(R.id.list_time);
                this.list_car= itemView.findViewById(R.id.list_car);
                this.btn_delete= itemView.findViewById(R.id.btn_delete);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),picture_management.class);
                        intent.putExtra("unique_name",unique_List.get(getAdapterPosition()));
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

            }

        }//customViewHolder

    }



}
