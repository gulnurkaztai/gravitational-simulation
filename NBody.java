public class NBody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments
        double tau = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        // Step 2. Read universe from standard input
        int n = StdIn.readInt(); // number of particles
        double radius = StdIn.readDouble(); // radius of the universe
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n]; // value of the mass
        String[] image = new String[n];
        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }
        // Step 3. Initialize standard drawing
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        // Step 4. Play music on standard audio
        StdAudio.play("2001.wav");
        double G = 6.67e-11;
        double[] fx = new double[n];
        double[] fy = new double[n];
        double[] ax = new double[n];
        double[] ay = new double[n];

        // Step 5. Simulate the universe
        for (double t = 0.0; t < tau; t += dt) {
            // System.out.println(t);
            for (int i = 0; i < image.length; i++) {
                // Step 5B: update the velocities and positions
                vx[i] += ax[i] * dt;
                vy[i] += ay[i] * dt;
                px[i] += vx[i] * dt;
                py[i] += vy[i] * dt;
                // fx[i] and fy[i] must be initialized to 0 at each time step
                fx[i] = 0;
                fy[i] = 0;
                // Step 5A: calculate the forces
                for (int j = 0; j < image.length; j++) {
                    // skip the case when i equals j
                    if (i != j) {

                        // distance between two bodies = square root of(Δx)2(square)+(Δy)2(square)
                        double r = Math.sqrt(Math.pow((px[j] - px[i]), 2) +
                                                     Math.pow((py[j] - py[i]), 2));
                        // F=G*m1*m2/square of r
                        double force = (G * (mass[i] * mass[j]) / Math.pow(r, 2));
                        // Fx = (F*Δx)/r
                        fx[i] += (force * (px[j] - px[i])) / r;
                        // Fy = (F*Δy)/r
                        fy[i] += (force * (py[j] - py[i])) / r;
                        // ax = fx/m
                        ax[i] = fx[i] / mass[i];
                        // ay = fy/m
                        ay[i] = fy[i] / mass[i];
                    }
                }
            }
            // Step 5C: draw universe to standard drawing
            StdDraw.clear();
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int k = 0; k < image.length; k++) {
                StdDraw.picture(px[k], py[k], image[k]);
            }
            StdDraw.show();
            StdDraw.pause(5);
        }
        // Step 6. Print universe to standard output
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }

    }

}


