package com.pixelall.rxstudy;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lxl on 2017/8/2.
 */

public class SwapRecycleView extends LinearLayout{
    private RecyclerView addressRecycle;
    private RecyclerView engRecycle;
    private LinearLayout rootView;
    private EngAdapter engAdapter;
    private AddAdapter addAdapter;
    private int sFirstPosition=0;
    //int sLastPosition=0;
    private static String[] eng = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private List<String> address;
    private boolean initViewFinish=false;
    private String result="";
    private OnSelectListener onSelectListener;

    public SwapRecycleView(Context context) {
        super(context);
        initView();
    }

    public SwapRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SwapRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public ViewTreeObserver getViewTreeObserver() {
        return super.getViewTreeObserver();
    }

    private void initView() {
        address=new ArrayList<>();
        rootView= (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_swap,this,false);
        /*int orientation=getOrientation();
        switch (orientation){
            case LinearLayout.HORIZONTAL:
                initHorizontalView();
                break;
            case LinearLayout.VERTICAL:
                initVerticalView();
                break;
        }*/
    }


    public String getResult(){
        return result;
    }

    public interface OnSelectListener{
        void selectData(String data);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener){
        this.onSelectListener=onSelectListener;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (initViewFinish) return;
        initVerticalView();
    }


    private void initVerticalView() {
        for (int i = 0; i < 5; i++) {
            address.add("阿址"+i);
        }

        for (int i = 0; i < 5; i++) {
            address.add("伯址"+i);
        }


        for (int i = 0; i < 6; i++) {
            address.add("次址"+i);
        }


        for (int i = 0; i < 7; i++) {
            address.add("地址"+i);
        }


        for (int i = 0; i < 6; i++) {
            address.add("e址"+i);
        }

        for (int i = 0; i < 6; i++) {
            address.add("发址"+i);
        }
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        addressRecycle= (RecyclerView) rootView.findViewById(R.id.add_recycle);
        addressRecycle.setLayoutManager(manager);
        addAdapter =new AddAdapter(address);
        addressRecycle.setAdapter(addAdapter);
        addAdapter.setOnItemViewClickListener(new BaseRecycleAdapter.OnItemViewClickListener<String>() {
            @Override
            public void itemViewClick(String s, int position) {
                result=s;
                addAdapter.changeClickView(position);
                if (onSelectListener!=null) onSelectListener.selectData(s);
            }
        });
        RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstPosition=manager.findFirstVisibleItemPosition();
                //int lastPosition=manager.findLastVisibleItemPosition();
                if (firstPosition!=0&&firstPosition==sFirstPosition) return;
                sFirstPosition=firstPosition;
               // sLastPosition=lastPosition;

                String addString=addAdapter.getAllData().get(firstPosition);
                //String lastAddString=addAdapter.getAllData().get(lastPosition);
                String firstEng=CharacterParser.getInstance().
                        convert(addString.substring(0,1));
//                String lastEng=CharacterParser.getInstance().
//                        convert(lastAddString.substring(0,1));
                char fc=firstEng.charAt(0);
                //char lc=lastEng.charAt(0);
                if (Character.isLowerCase(fc)){
                    fc=Character.toUpperCase(fc);
                }
//                if (Character.isLowerCase(lc)){
//                    lc=Character.toUpperCase(lc);
//                }
                int showFistPosition=fc-65;
                //int showLastPosition=lc-65;
                engAdapter.refreshPosition(showFistPosition);
            }
        };
        addressRecycle.addOnScrollListener(onScrollListener);

        LinearLayoutManager manager2=new LinearLayoutManager(getContext());
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        engRecycle= (RecyclerView) rootView.findViewById(R.id.eng_recycle);
        engAdapter=new EngAdapter(Arrays.asList(eng));
        engRecycle.setAdapter(engAdapter);
        engRecycle.setLayoutManager(manager2);

        engAdapter.setOnItemViewClickListener(new BaseRecycleAdapter.OnItemViewClickListener<String>() {
            @Override
            public void itemViewClick(String s, int position) {
                engAdapter.refreshPosition(position);
                List<String> addData=addAdapter.getAllData();
                for (int i=0;i<addData.size();i++){
                    String firstEng=CharacterParser.getInstance().
                            convert(addData.get(i).substring(0,1));
                    char fc=firstEng.charAt(0);
                    if (Character.isLowerCase(fc)){
                        fc=Character.toUpperCase(fc);
                    }
                    if (fc==position+65){
                        LinearLayoutManager ma = (LinearLayoutManager) addressRecycle.getLayoutManager();
                        ma.scrollToPositionWithOffset(i,0);
                        return;
                    }
                }
            }
        });
        removeAllViews();
        addView(rootView);
        initViewFinish=true;
       String addString= addAdapter.getAllData().get(0);
        String firstEng=CharacterParser.getInstance().
                convert(addString.substring(0,1));
//                String lastEng=CharacterParser.getInstance().
//                        convert(lastAddString.substring(0,1));
        char fc=firstEng.charAt(0);
        //char lc=lastEng.charAt(0);
        if (Character.isLowerCase(fc)){
            fc=Character.toUpperCase(fc);
        }
//                if (Character.isLowerCase(lc)){
//                    lc=Character.toUpperCase(lc);
//                }
        int showFistPosition=fc-65;
        //int showLastPosition=lc-65;
        engAdapter.refreshPosition(showFistPosition);
    }

    private void initHorizontalView() {

    }


    private class AddAdapter extends BaseRecycleAdapter<EngViewHolder,String>{
        private int clickPosition=-1;
        public AddAdapter(List<String> mineDataList) {
            super(mineDataList);
        }
        @Override
        protected EngViewHolder getViewHolder(ViewGroup parent) {
            int w=getWidth();
            TextView textView=new TextView(getContext());
            textView.setWidth(w-50);
            textView.setHeight(100);
            return new EngViewHolder(textView);
        }
        @Override
        protected void onMyBindViewHolder(EngViewHolder holder, int position, List<String> mineDataList) {
            holder.eng.setText(mineDataList.get(position));
            if (position==clickPosition){
                holder.eng.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        }

        public void changeClickView(int clickPosition){
            this.clickPosition=clickPosition;
            notifyDataSetChanged();
        }
    }




    private class EngViewHolder extends RecyclerView.ViewHolder{
        TextView eng;
        public EngViewHolder(View itemView) {
            super(itemView);
            eng= (TextView) itemView;
        }
    }

    private class EngAdapter extends BaseRecycleAdapter<EngViewHolder,String>{
        private int firstPosition=-1;
        public EngAdapter(List<String> mineDataList) {
            super(mineDataList);
        }
        @Override
        protected EngViewHolder getViewHolder(ViewGroup parent) {
            int h=getHeight();
            Log.i("获取的H",h+"");
            TextView textView=new TextView(getContext());
            textView.setWidth(50);
            textView.setHeight(h/eng.length);
            textView.setGravity(Gravity.CENTER);
            return new EngViewHolder(textView);
        }
        @Override
        protected void onMyBindViewHolder(EngViewHolder holder, int position, List<String> mineDataList) {
            holder.eng.setText(mineDataList.get(position));
            if (position==firstPosition){
                holder.eng.setTextSize(18);
                holder.eng.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }

        public void refreshPosition(int firstPosition){
            this.firstPosition=firstPosition;
            notifyDataSetChanged();
        }
    }


}
