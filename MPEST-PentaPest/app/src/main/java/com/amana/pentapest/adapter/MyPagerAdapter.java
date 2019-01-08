package com.amana.pentapest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.amana.pentapest.fragment.FeedbackFragment;
import com.amana.pentapest.fragment.MaterialFragment;
import com.amana.pentapest.fragment.PaymentFragment;
import com.amana.pentapest.fragment.PhotoAfterFragment;
import com.amana.pentapest.fragment.PhotoBeforeFragment;
import com.amana.pentapest.fragment.ServicesFragment;
import com.amana.pentapest.fragment.TeamsFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	private  List<Fragment> mFragmentList;
	private  List<String> mFragmentTitleList = new ArrayList<>();


	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		this.mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(new PhotoBeforeFragment()); // photo before
		mFragmentList.add(new ServicesFragment());
		mFragmentList.add(new MaterialFragment());
		mFragmentList.add(new PhotoAfterFragment()); // Photo after
		mFragmentList.add(new TeamsFragment());
		mFragmentList.add(new FeedbackFragment());
		mFragmentList.add(new PaymentFragment());

		mFragmentTitleList.add("Photos Before");
		mFragmentTitleList.add("Services");
		mFragmentTitleList.add("Materials");
		mFragmentTitleList.add("Photos After");
		mFragmentTitleList.add("Team Members");
		mFragmentTitleList.add("Feedback");
		mFragmentTitleList.add("Payment");
	/*	.addFrag(new PhotoBeforeFragment(), "Photos Before");
		adapter.addFrag(new ServicesFragment(), "Services");
		adapter.addFrag(new MaterialFragment(), "Materials");
		adapter.addFrag(new PhotoAfterFragment(), "Photos After");
		adapter.addFrag(new TeamsFragment(), "Team Members");
		adapter.addFrag(new FeedbackFragment(), "Feedback");
		adapter.addFrag(new PaymentFragment(), "Payment");*/
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}

	public void addFrag(Fragment fragment, String title) {
		mFragmentList.add(fragment);
		mFragmentTitleList.add(title);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mFragmentTitleList.get(position);
	}
}
