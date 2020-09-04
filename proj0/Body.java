// import java.lang.reflect.Array;

// import sun.tools.tree.ThisExpression;

public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
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
        // double distance = Math.pow(Math.pow((this.xxPos - b2.xxPos), 2) + Math.pow((this.yyPos - b2.yyPos), 2), .5);
        double distance = Math.sqrt(Math.pow(this.xxPos - b2.xxPos, 2) + Math.pow(this.yyPos - b2.yyPos, 2));
        return distance;
    }
    public double calcForceExertedBy(Body b2) {
        double GravitationalConstant = 6.67e-11;
        if (this.equals(b2)) {
            return 0;
        }
        return (GravitationalConstant* this.mass * b2.mass) / Math.pow(this.calcDistance(b2), 2);
    }
    public double calcForceExertedByX(Body b2) {
        // double force = this.calcForceExertedBy(b2);
        if (this.calcForceExertedBy(b2) == 0) {
            return 0;
        }
        // double xComp = b2.xxPos - this.xxPos;
        return this.calcForceExertedBy(b2) * (b2.xxPos - this.xxPos) / this.calcDistance(b2);
    }
    public double calcForceExertedByY(Body b2) {
        // double force = this.calcForceExertedBy(b2);
        if (this.calcForceExertedBy(b2) == 0) {
            return 0;
        }
        // double yComp = b2.yyPos - this.yyPos;
        return this.calcForceExertedBy(b2) * (b2.yyPos - this.yyPos) / this.calcDistance(b2);
    }
    public double calcNetForceExertedByX(Body[] bodies) {
        double calcNetX= 0; 
        for (Body planet : bodies) {
            if (this.equals(planet)) {
                continue;
            }
            calcNetX += calcForceExertedByX(planet);
        }
        return calcNetX;
    }
    public double calcNetForceExertedByY(Body[] bodies) {
        double calcNetY = 0;
        for (Body planet : bodies) {
            if (this.equals(planet)) {
                continue;
            }
            calcNetY += calcForceExertedByY(planet);
        }
        return calcNetY;
    }
    public void update(double dt, double fX, double fY) {
        double aNetX = fX / this.mass;
        double aNetY = fY / this.mass;
        double vX = this.xxVel + dt * aNetX;
        double vY = this.yyVel + dt * aNetY;
        double pX = this.xxPos + dt * vX;
        double pY = this.yyPos + dt * vY;
        this.xxVel = vX;
        this.yyVel = vY; 
        this.xxPos = pX;
        this.yyPos = pY;
        return;                                                                                                                                                                                 
    }
    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
        return;
    }
}