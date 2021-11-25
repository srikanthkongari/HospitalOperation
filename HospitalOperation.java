package com.edyoda.classroom;

public class HospitalOperation {

    // Private class variables
    private static HospitalOperation _instance;
    private static HospitalOperation
            _instanceForDoubleCheckLocking;
    private boolean empty = false;
    private String patientName = "default";

    // Method 1
    // Displays Instance created only when new Instance is
    // Created
    private HospitalOperation()
    {
        System.out.println("Instance Created");
    }

    // Method 2
    // Synchronized method() Approach
    public static synchronized HospitalOperation
    getInstanceSynchronizedWay()
    {

        if (_instance == null)
            _instance = new HospitalOperation();

        return _instance;
    }

    // Method 3
    // Double Checked Locking- Synchronized Block
    public static HospitalOperation
    getInstanceSynchronizedBlockWay()
    {

        // Checking for double locking
        if (_instanceForDoubleCheckLocking == null)
            synchronized (HospitalOperation.class)
            {
                if (_instanceForDoubleCheckLocking == null)
                    _instanceForDoubleCheckLocking
                            = new HospitalOperation();
            }

        return _instanceForDoubleCheckLocking;
    }

    // Method 4
    // Checks if operation theatre is empty or not
    public boolean isOperationTheatreEmpty()
    {
        return empty;
    }

    // Method 5
    // Called when Operation is finished
    public void endOperation() { empty = true; }

    // Method 6
    // Accessed by more than one threads
    public synchronized void operation(String aName)
    {

        // When flag variables changes from false to true
        if (empty == true) {
            patientName = aName;

            // Get the patient ready as operation can be
            // performed
            System.out.println("Operation can be done "
                    + "get ready patient "
                    + patientName);
            empty = false;
        }

        // Operation can not be performed
        else {
            // Print and display
            System.out.println(
                    "Sorry " + aName
                            + " Operation Theatre is busy with Surgery of "
                            + patientName);
        }
    }
}

    // Class 2
// Main class
    class Hospital {

        // Main driver method
        public static void main(String args[])
        {

            // Synchronized method

            // Now creating a thread in main() method
            Thread t1 = new Thread(new Runnable() {
                // run() method for this thread
                public void run()
                {

                    // Creating object of above class in
                    // this class main() method
                    HospitalOperation i1
                            = HospitalOperation
                            .getInstanceSynchronizedWay();

                    // Print statement only
                    System.out.println(
                            "The instance 1 in Synchronized Method is "
                                    + i1);

                    // Calling the method
                    // passing custom argument as input
                    i1.endOperation();
                    i1.operation("123");
                }
            });

            // Thread 2
            // Again creating another thread
            Thread t2 = new Thread(new Runnable() {
                // run() method for this thread
                public void run()
                {

                    HospitalOperation i2
                            = HospitalOperation
                            .getInstanceSynchronizedWay();

                    System.out.println(
                            "The instance 2 in Synchronized Method is "
                                    + i2);
                    i2.operation("789");
                }
            });

            // We delay thread also to ensure that
            // sequence of output is correct

            // Starting the first thread
            // using start() method for threads
            t1.start();

            // try {
            //     Thread.sleep(1000);
            // }
            // catch (InterruptedException e)
            //     {}

            //  Similarly, starting the second thread
            t2.start();

            // Double Checked Locking

            // Print statement only
            System.out.println(
                    "Double Checked locking - Synchronized Block only");

            // Thread 3
            // Again creating a thread using runnable ineterface
            Thread t3 = new Thread(new Runnable() {
                // run() method for this thread
                public void run()
                {

                    HospitalOperation i1
                            = HospitalOperation
                            .getInstanceSynchronizedBlockWay();

                    System.out.println(
                            "The instance 1 in Double Checked Locking way is "
                                    + i1);

                    i1.endOperation();
                    i1.operation("ABC");
                }
            });

            // Thread 4
            // LAstly creating anotherr thread
            Thread t4 = new Thread(new Runnable() {
                // run() method for this thread
                public void run()
                {
                    HospitalOperation i2
                            = HospitalOperation
                            .getInstanceSynchronizedBlockWay();

                    System.out.println(
                            "The instance 2 in Double Checked Locking way is "
                                    + i2);

                    i2.operation("XYZ");
                }
            });
            // We delay thread also to ensure that
            // sequence of output is correct
            // try {
            //     Thread.sleep(1000);
            // }
            // catch (InterruptedException e)
            //     {}
            t3.start();
            // try {
            //     Thread.sleep(1000);
            // }
            // catch (InterruptedException e)
            //     {}
            t4.start();
        }
    }


