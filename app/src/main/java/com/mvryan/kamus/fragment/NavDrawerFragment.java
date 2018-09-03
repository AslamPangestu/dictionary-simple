package com.mvryan.kamus.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvryan.kamus.MainActivity;
import com.mvryan.kamus.R;
import com.mvryan.kamus.model.NavDrawerItem;
import com.mvryan.kamus.utils.adapter.NavDrawerAdapter;
import com.mvryan.kamus.utils.listener.ClickListener;
import com.mvryan.kamus.utils.listener.NavDrawerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mvryan on 29/08/18.
 */

public class NavDrawerFragment extends Fragment{

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavDrawerAdapter adapter;
    private View drawerView;
    private static String[] titles = null;
    private NavDrawerListener fragmentDrawerListener;

    private TextView welcomeTxt;

    public NavDrawerFragment() {
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> navDrawerItems = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem item = new NavDrawerItem();
            item.setTitle(titles[i]);
            navDrawerItems.add(item);
        }
        return navDrawerItems;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_items);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        recyclerView = layout.findViewById(R.id.list_drawer_items);
        welcomeTxt = layout.findViewById(R.id.welcome_nav_drawer);

        welcomeTxt.setText(R.string.welcome);

        adapter = new NavDrawerAdapter(getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecycleTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                fragmentDrawerListener.onDrawerItemSelected(view, position);
                drawerLayout.closeDrawer(drawerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }

    public void setUp(int fragmentId, DrawerLayout mDrawerLayout, final Toolbar toolbar) {
        drawerView = getActivity().findViewById(fragmentId);
        drawerLayout = mDrawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });

    }

    public void setFragmentDrawerListener(NavDrawerListener fragmentDrawerListener) {
        this.fragmentDrawerListener = fragmentDrawerListener;
    }

    static class RecycleTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecycleTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
