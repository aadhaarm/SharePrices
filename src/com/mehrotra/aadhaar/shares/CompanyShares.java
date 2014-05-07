package com.mehrotra.aadhaar.shares;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CompanyShares {

	private static Map<Integer, String> mapCompanies;
	private static Map<Integer, String> mapMonthYear;
	private static Map<Integer, Integer> mapMaxPriceCompany;	
	private static Map<Integer, SharePrice> mapCompanyYearMonth;	

	/**
	 * Reads a CSV with various companies share prices and reports
	 * 1. Leading company every Year/Month with maximum share prices between other companies
	 * 2. Best share prices for a company so far in all the years/months
	 * @param args
	 */
	public static void main(String[] args) {
		String csvFile = "CompanyShares.csv";
		mapCompanies = new HashMap<>();
		mapMonthYear = new HashMap<>();
		mapMaxPriceCompany = new HashMap<>();
		mapCompanyYearMonth = new HashMap<Integer, SharePrice>();
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";


		try {

			InputStream inputStream = new CompanyShares().getClass().getClassLoader().getResourceAsStream(csvFile);
			br = new BufferedReader(new InputStreamReader(inputStream ));

			int row = 0;

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] companySharePriseList = line.split(cvsSplitBy);

				if(companySharePriseList != null) {

					// Save month/year string with row
					saveMonthYearInMap(row, companySharePriseList);
					
					// Get company Names from first row
					if(row == 0) {
						saveCompaniesNamesInMap(companySharePriseList);
					}
					
					// Get maximum share price for a year/month between all companies
					if(row > 0) {
						findLeadCompanySharePrices(row, companySharePriseList);
					}
					
					// Get each company maximum share price in year
					if(row > 0) {
						findMaximumSharePriceForEachCompany(companySharePriseList);
					}
				}

				row++;
			}

			// Print list of companies
			System.out.println("List of companies");
			for (String companyName : mapCompanies.values()) {
				System.out.println("Name: " + companyName);
			}
			
			// Print max share price company per year
			System.out.println("Max share price company per year");
			row = 1;
			for (Integer companyIndex : mapMaxPriceCompany.values()) {
				System.out.println("For Month/Year: " + mapMonthYear.get(row) + " : Leading Company with maximum share prices: " + mapCompanies.get(companyIndex));
				row++;
			}
			
			// Print each company best year and maximum share prices
			System.out.println("Each company best year/month and maximum share prices");
			row = 0;
			for (SharePrice sharePrice : mapCompanyYearMonth.values()) {
				System.out.println(mapCompanies.get(row) + " has best share value so far " + sharePrice.getPrice() + " in " + sharePrice.getMonth() + "/" + sharePrice.getYear());
				row++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

	private static void findMaximumSharePriceForEachCompany(
			String[] companySharePriseList) {
		int column = 0;
		
		for (String csvItem : companySharePriseList) {
			if(column == 0 || column == 1) {
				column++;
				continue;
			}
			
			int sharePrice = Integer.parseInt(csvItem);
			
			if(mapCompanyYearMonth.get(column - 2) == null) {
				mapCompanyYearMonth.put(column - 2, new SharePrice(companySharePriseList[0], companySharePriseList[1], sharePrice));
			} else {
				SharePrice bestSharePrice = mapCompanyYearMonth.get(column-2);
				if(bestSharePrice != null) {
					if(bestSharePrice.getPrice() < sharePrice) {
						mapCompanyYearMonth.put(column - 2, new SharePrice(companySharePriseList[0], companySharePriseList[1], sharePrice));
					}
				}
			}
			
			column++;
		}
	}

	private static void findLeadCompanySharePrices(int row,
			String[] companySharePriseList) {
		int column = 0;
		int maxSharePrice = 0;
		int companyIndex_maxSharePrice = -1;
		
		for (String csvItem : companySharePriseList) {
			if(column == 0 || column == 1) {
				column++;
				continue;
			}
			
			int sharePrice = Integer.parseInt(csvItem);
			if(sharePrice > maxSharePrice) {
				maxSharePrice = sharePrice;
				companyIndex_maxSharePrice = column;
			}
			
			column++;
		}
		
		mapMaxPriceCompany.put(row, companyIndex_maxSharePrice - 2);
	}

	private static void saveCompaniesNamesInMap(String[] companySharePriseList) {
		int column = 0;
		for (String csvItem : companySharePriseList) {
			if(column == 0 || column == 1) {
				column++;
				continue;
			}
			mapCompanies.put(column - 2, csvItem);
			column++;
		}
	}

	private static void saveMonthYearInMap(int row, String[] companySharePriseList) {
		mapMonthYear.put(row, companySharePriseList[1] + "/" + companySharePriseList[0]);
	}
}