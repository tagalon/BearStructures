public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public double GravitationalConstant = 6.67 *  10e-11;
    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }
    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    public double calcDistance(Body b2) {
        double distance = Math.pow((this.xxPos - b2.xxPos) * (this.xxPos - b2.xxPos) + (this.yyPos - b2.yyPos) * (this.yyPos - b2.yyPos), .5);
        return distance;
    }
    public double calcForceExertedBy(Body b2) {
        return (GravitationalConstant * this.mass * b2.mass) / (Math.pow((this.calcDistance(b2)), 2));
    }
}