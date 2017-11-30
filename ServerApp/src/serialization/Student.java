package serialization;

import java.io.Serializable;

public class Student implements Serializable{
    static final long serialVersionUID = 42L;
    public int nrIndexu;

    public Student (int nrIndexu)
    {
        this.nrIndexu = nrIndexu;
    }
}