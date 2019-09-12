package de.muc.jtm.frog_jump_count;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class FrogJumpCountCalculator {

  public static void main(String[] args) {
    int max = 10_000;

    Fraction sum = Fraction.ZERO;
    Fraction preStepValue = Fraction.ZERO;

    for (int n = 1; n <= max; n++) {
      Fraction stepValue = Fraction.ONE.add(sum.divide(n));
      Fraction stepDifference = stepValue.sub(preStepValue);
      preStepValue = stepValue;

      sum = sum.add(stepValue);

      System.out.printf("N: %5d -> %3.5f -> %1.6f \t %s\n", n, stepValue.doubleValue(),
          stepDifference.doubleValue(), stepValue.toString());
    }
  }


  public static class Fraction {
    public static Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);
    public static Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);

    BigInteger numerator, denominator;

    public Fraction(BigInteger numerator, BigInteger denominator) {
      BigInteger gcd = numerator.gcd(denominator);

      if (gcd.compareTo(BigInteger.ONE) > 0) {
        this.numerator = numerator.divide(gcd);
        this.denominator = denominator.divide(gcd);
      } else {
        this.numerator = numerator;
        this.denominator = denominator;
      }
    }

    public Fraction divide(Fraction other) {
      BigInteger numerator = this.numerator.multiply(other.denominator);
      BigInteger denominator = this.denominator.multiply(other.numerator);

      return new Fraction(numerator, denominator);
    }

    public Fraction divide(long value) {
      return divide(BigInteger.valueOf(value));
    }

    public Fraction divide(BigInteger value) {
      BigInteger numerator = this.numerator;
      BigInteger denominator = this.denominator.multiply(value);

      return new Fraction(numerator, denominator);
    }

    public Fraction add(Fraction other) {
      BigInteger thisNumerator = this.numerator.multiply(other.denominator);
      BigInteger otherNumerator = other.numerator.multiply(this.denominator);

      BigInteger numerator = thisNumerator.add(otherNumerator);
      BigInteger denominator = this.denominator.multiply(other.denominator);

      return new Fraction(numerator, denominator);
    }

    public Fraction sub(Fraction other) {
      BigInteger thisNumerator = this.numerator.multiply(other.denominator);
      BigInteger otherNumerator = other.numerator.multiply(this.denominator);

      BigInteger numerator = thisNumerator.subtract(otherNumerator);
      BigInteger denominator = this.denominator.multiply(other.denominator);

      return new Fraction(numerator, denominator);
    }

    public double doubleValue() {
      BigDecimal num = new BigDecimal(numerator);
      BigDecimal den = new BigDecimal(denominator);

      @SuppressWarnings("deprecation")
      BigDecimal val = num.divide(den, 10, BigDecimal.ROUND_FLOOR);

      return val.doubleValue();
    }

    @Override
    public String toString() {
      NumberFormat formatter =
          new DecimalFormat("0.####E0", DecimalFormatSymbols.getInstance(Locale.ROOT));

      return "Fraction[ " + formatter.format(numerator) + " / " + formatter.format(denominator)
          + " ]";
    }


  }
}
