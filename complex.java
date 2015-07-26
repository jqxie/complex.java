/* Name: Jessica Xie
 * User: jqxie
 * Assignment: Program Assignment #6
 * This program represents complex numbers as a pair of doubles.
 */

class Complex{
  //private data fields
  private double re;
  private double im;
  //public constant fields
  public static final Complex ONE = Complex.valueOf(1,0); //complex number one
  public static final Complex ZERO = Complex.valueOf(0,0); //complex number zero
  public static final Complex I = Complex.valueOf(0,1); //imaginary unit

  //constructors
  Complex(double a, double b){
    this.re = a;
    this.im = b;
  }

  Complex(double a){
    this.re = a;
    this.im = 0;
  }

  Complex(String s){
    //should accept expressions like -2+3i, 2-3i, 3, 5i
    //throw a numberFormatException if s cannot be parsed as complex
    double[] C = parseComplex(s);
    this.re = C[0];
    this.im = C[1];
  }

  static double[] parseComplex(String str){
      double[] part = new double[2];
      String s = str.trim();
      String NUM = "(\\d+\\.\\d*|\\.?\\d+)";
      String SGN = "[+-]?";
      String OP =  "\\s*[+-]\\s*";
      String I =   "i";
      String OR =  "|";
      String REAL = SGN+NUM;
      String IMAG = SGN+NUM+"?"+I;
      String COMP = REAL+OR+
                    IMAG+OR+
                    REAL+OP+NUM+"?"+I;

      if( !s.matches(COMP) ){
         throw new NumberFormatException(
                   "Cannot parse input string \""+s+"\" as Complex");
      }
      s = s.replaceAll("\\s","");
      if( s.matches(REAL) ){
         part[0] = Double.parseDouble(s);
                  part[1] = 0;
      }else if( s.matches(SGN+I) ){
         part[0] = 0;
         part[1] = Double.parseDouble( s.replace( I, "1.0" ) );
      }else if( s.matches(IMAG) ){
         part[0] = 0;
         part[1] = Double.parseDouble( s.replace( I , "" ) );
      }else if( s.matches(REAL+OP+I) ){
         part[0] = Double.parseDouble( s.replaceAll( "("+REAL+")"+OP+".+" , "$1" ) );
         part[1] = Double.parseDouble( s.replaceAll( ".+("+OP+")"+I , "$1"+"1.0" ) );
      }else{   //  s.matches(REAL+OP+NUM+I) 
         part[0] = Double.parseDouble( s.replaceAll( "("+REAL+").+"  , "$1" ) );
         part[1] = Double.parseDouble( s.replaceAll( ".+("+OP+NUM+")"+I , "$1" ) );
      }
      return part;
 }

  //public methods
  //Complex arithmetic
  //==================
  //copy(): return a new complex equal to this complex
  Complex copy(){
    return new Complex (this.re, this.im); //?
  }

  //add(): return a new complex representing the sum this plus z
  Complex add(Complex z){
    this.re = re + z.re;
    this.im = im + z.im;
    return new Complex(this.re, this.im);
  }

  //negate(): return complex representing the negative of this
  Complex negate(){
    return new Complex(-re, -im);
  }

  //sub(): return a new complex representing the difference this minus z
  Complex sub(Complex z){
    this.re = re - z.re;
    this.im = im - z.im;
    return new Complex(this.re, this.im);
  }

  //mult(): return a new complex representing the product this times z
    Complex mult(Complex z){
    return new Complex(this.re*z.re-this.im*z.im, this.re*z.im+this.im*z.re);
  }

  //recip(): return a new complex representing the reciprocal of this
  //Throw an ArithmeticException with appropriate message if
  //this.equals(Complex.ZERO).
  Complex recip(){
    if(this.equals(Complex.ZERO)){
      throw new ArithmeticException();
    }
    double sc = re*re + im*im;
    return new Complex(re/sc, -im/sc);
  }

  //div(): return a complex representing the quotient of this by z
  //Throw an ArithmeticException with appropriate message if
  //z.equals(Complex.ZERO)
  Complex div(Complex z){
    if (z.equals(Complex.ZERO)){
       throw new ArithmeticException("Cannot divide by zero");
    }

    double sr = z.re*z.re+z.im*z.im;
    return new Complex((this.re*z.re+this.im*z.im)/sr, (this.im*z.re-this.re*z.im)/sr);
//    return new Complex(div);
  }

  //conj(): return a complex representing the conjugate of this complex
  Complex conj(){
    return new Complex(re, -im);
  }

  //re(): return the real part
  double Re(){
    return re;
  }

  //im(): return the imaginal part
  double Im(){
    return im;
  }

  //abs(): return the modulus of this complex (distance between points)
    double abs(){
    if(this.re!=0 || this.im!=0){
      return Math.sqrt(this.re*this.re+this.im*this.im);
    } else{
      return 0;
    }
  }

  //arg(): return the argument of this complex (angle this complex makes
  //with positive real axis
  double arg(){
    return Math.atan2(im, re);
  }

  //Other functions
  //toString(): return a string representation of this complex.
  public String toString(){
    if(this.re!=0 && this.im>0){
       return this.re + "+" + this.im + "i";
    } if(this.re!=0 && this.im<0){
        return this.re + "-" + (-this.im) + "i";
    } if(this.im==0){
        return String.valueOf(this.re);
    } if(this.re==0){
        return this.im + "i";
    } //Infinity || Not a number 
      return this.re + "+i*" + this.im;
  }

  //equals(): return true iff this and obj have the same re and im parts
  public boolean equals(Object obj){
  Complex x = new Complex(this.re, this.im);
   if(x == (Complex)obj){
      return true;
    } else{
        return false;
    }
  }

  //valueOf(): return complex with real part a and imaginary part b
  static Complex valueOf(double a, double b){
   return new Complex(a, b); //?
  }

  //valueOf(): return a new complex with real part a and imaginary part 0
  static Complex valueOf(double a){
    return new Complex(a, 0); //?
  }

  //valueOf(): return complex constructed from s
  static Complex valueOf(String s){
   return new Complex(s);
  }
}
