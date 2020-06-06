package TaxDemo.com.tax.domain.elements;

//line 72 "../../../../../../../ump/tmpjntyadj0/model.ump"
//line 324 "../../../../../../../ump/tmpjntyadj0/model.ump"
public abstract class Tax extends Deduction {

//------------------------
// MEMBER VARIABLES
//------------------------

//------------------------
// CONSTRUCTOR
//------------------------

	public Tax(Employee aEmployee) {
		super(aEmployee);
	}

//------------------------
// INTERFACE
//------------------------

	public void delete() {
		super.delete();
	}


	/**
	 * checks acceptable taxpercentage inputs
	 * checking is done in two parts
	 *
	 * @param taxPercentages an array of tax percentages
	 * @param depth         defines the depth of income category
	 * @return accummulated taxes above the depth
	 */
// line 86 "../../../../../../../ump/tmpjntyadj0/model.ump"
	protected double computeTaxCategoryPay(double[] taxPercentages, double[] lowerBounds, int depth) {
		//defensive programming
		assert isIncreasing(taxPercentages) == true : "Unacceptable tax brackets -- should be of length 4 and must be in increasing order";
		assert isIncreasing(lowerBounds) == true : "Unacceptable tax brackets -- should be of length 4 and must be in increasing order";
		assert depth >= 0 && depth < 6 : "Acceptable tax depth ranges between [0 .. 3]";

		//computes taxes recursively level by level

		// Edited to accept more tax brackets as BC has 7
		switch (depth) {
			case 0:
				return (lowerBounds[0] * taxPercentages[0]);
			case 1:
				return (lowerBounds[1] - lowerBounds[0]) * taxPercentages[1] + computeTaxCategoryPay(taxPercentages, lowerBounds, 0);
			case 2:
				return (lowerBounds[2] - lowerBounds[1]) * taxPercentages[2] + computeTaxCategoryPay(taxPercentages, lowerBounds, 1);
			case 3:
				return (lowerBounds[3] - lowerBounds[2]) * taxPercentages[3] + computeTaxCategoryPay(taxPercentages, lowerBounds, 2);
			case 4:
				return (lowerBounds[4] - lowerBounds[3]) * taxPercentages[4] + computeTaxCategoryPay(taxPercentages, lowerBounds, 3);
			case 5:
				return (lowerBounds[5] - lowerBounds[4]) * taxPercentages[5] + computeTaxCategoryPay(taxPercentages, lowerBounds, 4);
			default:
				return 0;
		}
	}


	/**
	 * checks acceptable taxpercentage inputs
	 * checking is done in two parts
	 *
	 * @param taxPercentage an array of tax percentages
	 * @return <code>true</code> if tax percentages are in increasing order, otherwise <code>false</code>
	 */
// line 113 "../../../../../../../ump/tmpjntyadj0/model.ump"
	// Altered method to allow taxPercentage array to be larger than 4 to allow for BC
	private boolean isIncreasing(double[] taxPercentage) {
		//checks the length of the array (in Canada, size must be 4)
		if (taxPercentage.length > 7)
			return false;

		//part 2 - checking whether the elemnts of the array is increasing
		double temp = taxPercentage[0];
		for (int i = 1; i < taxPercentage.length; i++) {

			if (temp > taxPercentage[i])
				return false;

			temp = taxPercentage[i];
			i++;
		}

		return true;
	}

}