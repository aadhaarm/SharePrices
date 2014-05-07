package com.mehrotra.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mehrotra.aadhaar.shares.CompanyShares;
import com.mehrotra.aadhaar.shares.SharePrice;

public class TestSharePriceAnalyzer {

	@Test
	public void testGatherCompanyNames() {
		String[] args = {"TestCSV-CompanyShares.csv"};
		
		CompanyShares.main(args);
		
		/*
		 * Number of companies recognized is 3
		 */
		int numberOfCompanies = CompanyShares.getMapCompanies().size(); 

		assertTrue(numberOfCompanies == 3);
	}

	@Test
	public void testMaxPriceCompany() {
		String[] args = {"TestCSV-CompanyShares.csv"};
		
		CompanyShares.main(args);
		
		/*
		 * For Apr/1990 (4th Row in CSV), maximum share prices is for CompanyB
		 */
		assertTrue(CompanyShares.getMapMaxPriceCompany().get(4) == 1);
	}

	@Test
	public void testCompanyYearMonth() {
		String[] args = {"TestCSV-CompanyShares.csv"};
		
		CompanyShares.main(args);
		
		/*
		 * For CompanyB, maximum share prices reached in Nov/1990. Share price 53
		 */
		assertTrue(CompanyShares.getMapCompanyYearMonth().get(1) == new SharePrice("1990", "Nov", 53));
	}

}
