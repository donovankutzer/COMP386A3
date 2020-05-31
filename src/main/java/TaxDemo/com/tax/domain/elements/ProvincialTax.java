package TaxDemo.com.tax.domain.elements;

//line 138 "../../../../../../../ump/tmpjntyadj0/model.ump"
//line 334 "../../../../../../../ump/tmpjntyadj0/model.ump"
public class ProvincialTax extends Tax {

//------------------------
// MEMBER VARIABLES
//------------------------

//------------------------
// CONSTRUCTOR
//------------------------

	public ProvincialTax(Employee aEmployee) {
		super(aEmployee);
		// line 145 "../../../../../../../ump/tmpjntyadj0/model.ump"
		setAmount(computeTaxes(grossIncome(), getProvince()));
		// END OF UMPLE AFTER INJECTION
	}

//------------------------
// INTERFACE
//------------------------

	public void delete() {
		super.delete();
	}

	/**
	 * operation computes provincial taxes for the the province of alberta
	 *
	 * @param grossIncome  employee's gross income prior to any tax deduction
	 * @param provinceName employee's work province
	 * @return provincialTax deductible provincial tax
	 */
// line 192 "../../../../../../../ump/tmpjntyadj0/model.ump"
	public double computeTaxes(double grossIncome, String provinceName) {
		//province's name is converted to all lower case letters
		String name = provinceName.toLowerCase();
		switch (name) {

			case "alberta":
				return alberta(grossIncome);
			case "british columbia":
				return britishcolumbia(grossIncome);
			case "manitoba":
				return 0.0;
			case "quebec":
				return 0.0;
			case "saskatchewan":
				return 0.0;
			case "newfoundland":
				return 0.0;
			default:
				return 0.0;
		}
	}

	// Donovan Ollenberger-Kutzer
    private double britishcolumbia(double grossIncome) {
		//setting up tax percentages and their respective lower bounds
		double[] taxPercentages = {0.0506, 0.0770, 0.1050, 0.1229, 0.1470, 0.1680, 0.2050};
		double[] lowerBounds = {41725.0, 83451.0, 95812.0, 116344.0, 157748.0, 220000.0};

		//provincialTaxBracket = "5.06% [$0 .. $41725)
		if (grossIncome >= 0.0 && grossIncome < 41725.00) {
			return 0.0506 * grossIncome;
		}

		//provincialTaxBracket = "7.7% [$41725 .. $83451.00)
		else if (grossIncome >= 41725.00 && grossIncome < 83451.00) {
			return (grossIncome - 41725.00) * .0770 + computeTaxCategoryPay(taxPercentages, lowerBounds, 0);
		}

		//provincialTaxBracket = "10.50% [$83451.00 .. $95812.00)
		else if (grossIncome >= 83451.00 && grossIncome < 95812.00) {
			return (grossIncome - 83451.00) * .1050 + computeTaxCategoryPay(taxPercentages, lowerBounds, 1);
		}

		//provincialTaxBracket = "12.29% [$95812.01 .. $116344)
		else if (grossIncome >= 95812.00 && grossIncome < 116344.00) {
			return (grossIncome - 95812.00) * .1229 + computeTaxCategoryPay(taxPercentages, lowerBounds, 2);
		}

		//provincialTaxBracket = "14.70% [116344.00 .. $157748.00)
		else if (grossIncome >= 116344.00 && grossIncome < 157748.00) {
			return (grossIncome - 116344.00) * .1470 + computeTaxCategoryPay(taxPercentages, lowerBounds, 3);
		}

		//provincialTaxBracket = "16.80% [157748.00 .. $220000.00)
		else if (grossIncome >= 157748.00 && grossIncome < 220000.00) {
			return (grossIncome - 157748.00) * .1680 + computeTaxCategoryPay(taxPercentages, lowerBounds, 4);
		}

		//provincialTaxBracket = "20.5% [$220000.00 .. )
		else {
			return (grossIncome - 220000) * .2050 + computeTaxCategoryPay(taxPercentages, lowerBounds, 5);
		}
    }

    /**
	 * operation computes provincial taxes for the the province of alberta
	 *
	 * @param grossIncome employee's gross income prior to any tax deduction
	 * @return provincialTax deductible provincial tax
	 */
// line 155 "../../../../../../../ump/tmpjntyadj0/model.ump"
	private double alberta(double grossIncome) {
		//setting up tax percentages and their respective lower bounds
		double[] taxPercentages = {0.1, 0.12, 0.13, 0.14};
		double[] lowerBounds = {131220.0, 157464.0, 209952.0, 314928.0};

		//provincialTaxBracket = "10.0% [$0 .. $131,220.01)
		if (grossIncome >= 0.0 && grossIncome < 131220.01) {
			return 0.1 * grossIncome;
		}

		//provincialTaxBracket = "12.0% [$131,220.01 .. $157,464.01)
		else if (grossIncome >= 131220.01 && grossIncome < 157464.01) {
			return (grossIncome - 131220.0) * .12 + computeTaxCategoryPay(taxPercentages, lowerBounds, 0);
		}

		//provincialTaxBracket = "13.0% [$157,464.01 .. $209,952.01)
		else if (grossIncome >= 157464.01 && grossIncome < 209952.01) {
			return (grossIncome - 157464.0) * .13 + computeTaxCategoryPay(taxPercentages, lowerBounds, 1);
		}

		//provincialTaxBracket = "14.0% [$209,952.01 .. $314,928.00)
		else if (grossIncome > 209952.01 && grossIncome < 314928.00) {
			return (grossIncome - 209952.0) * .14 + computeTaxCategoryPay(taxPercentages, lowerBounds, 2);
		}

		//provincialTaxBracket = "15.0% [$314,928.01 .. )
		else {
			return (grossIncome - 314928.0) * .15 + computeTaxCategoryPay(taxPercentages, lowerBounds, 3);
		}
	}

}