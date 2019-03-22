package com.amana.MPESTPestoff.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.amana.MPESTPestoff.jobtabs.NewEndServicesFragment;
import com.amana.MPESTPestoff.jobtabs.NewFeedbackFragment;
import com.amana.MPESTPestoff.jobtabs.NewInfestationLevelFragment;
import com.amana.MPESTPestoff.jobtabs.NewPaymentFragment;
import com.amana.MPESTPestoff.jobtabs.NewServicesFragment;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	private  List<Fragment> mFragmentList;
	private  List<String> mFragmentTitleList = new ArrayList<>();


	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		this.mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(new NewServicesFragment());
		mFragmentList.add(new NewFeedbackFragment());
		mFragmentList.add(new NewInfestationLevelFragment()); // Photo after
		mFragmentList.add(new NewPaymentFragment());
		mFragmentList.add(new NewEndServicesFragment());

		mFragmentTitleList.add("Services");
		mFragmentTitleList.add("Feedback");
		mFragmentTitleList.add("Infestation Level");
		mFragmentTitleList.add("Payment");
		mFragmentTitleList.add("End Service");

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
