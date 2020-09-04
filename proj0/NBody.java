public class NBody {
    public static double readRadius(String filename) {
        In inFile = new In (filename);
        double planet = inFile.readInt();
        double radius = inFile.readDouble();
        return radius;
    }
    public static Body[] readBodies(String filename) {
        In inFile = new In (filename);
        int numBodies = inFile.readInt();
        double radius = inFile.readDouble();
        Body[] bodies = new Body[numBodies];
        int body_index = 0;
        while (body_index < bodies.length) {
            double xxCord = Double.valueOf(inFile.readString());
            double yyCord = Double.valueOf(inFile.readString());
            double xxVel = Double.valueOf(inFile.readString());
            double yyVel = Double.valueOf(inFile.readString());
            double mass = Double.valueOf(inFile.readString());
            String file = inFile.readString();
            bodies[body_index] = new Body(xxCord, yyCord, xxVel, yyVel, mass, file);
            body_index += 1;
        }
        return bodies;
    }
    public static void main(String[] args) {
        double T = Double.valueOf(args[0]);
        double dT = Double.valueOf(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.picture(0, 0, "images/starfield.jpg");
        Body[] bodies = readBodies(filename);
        // for (Body planet: bodies) {
        //     planet.draw();
        //     double time = 0;
        //     StdDraw.enableDoubleBuffering();
        //     while (time <= T) {
        //         double[] xForces = new double[bodies.length];
        //         double[] yForces = new double[bodies.length];
        //         planet.calcNetForceExertedbyX(bodies);
        //         planet.calcNetForceExertedbyY(bodies);
                
        //         time += dT;
        //     }
        // }
        StdDraw.enableDoubleBuffering();
        double time = 0;
        while (time <= T) {
            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];
            for (int index = 0; index < bodies.length; index +=1) {
                xForces[index] = bodies[index].calcNetForceExertedByX(bodies);
                yForces[index] = bodies[index].calcNetForceExertedByY(bodies);
            }
            for (int index = 0; index < bodies.length; index +=1) {
                bodies[index].update(time, xForces[index], yForces[index]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Body planet: bodies) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dT;
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }    
    }
}
