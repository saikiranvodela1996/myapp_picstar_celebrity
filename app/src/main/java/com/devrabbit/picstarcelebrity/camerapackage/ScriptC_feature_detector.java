package com.devrabbit.picstarcelebrity.camerapackage;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.renderscript.Type;

/**
 * @hide
 */
public class ScriptC_feature_detector extends ScriptC {
    private static final String __rs_resource_name = "feature_detector";
    // Constructor
    public  ScriptC_feature_detector(RenderScript rs) {
        super(rs,
              __rs_resource_name,
           feature_detectorBitCode.getBitCode32(),
           feature_detectorBitCode.getBitCode64());
        __F32 = Element.F32(rs);
        __ALLOCATION = Element.ALLOCATION(rs);
        __U8_4 = Element.U8_4(rs);
        __U8 = Element.U8(rs);
    }

    private Element __ALLOCATION;
    private Element __F32;
    private Element __U8;
    private Element __U8_4;
    private FieldPacker __rs_fp_ALLOCATION;
    private FieldPacker __rs_fp_F32;
    private final static int mExportVarIdx_corner_threshold = 0;
    private float mExportVar_corner_threshold;
    public synchronized void set_corner_threshold(float v) {
        setVar(mExportVarIdx_corner_threshold, v);
        mExportVar_corner_threshold = v;
    }

    public float get_corner_threshold() {
        return mExportVar_corner_threshold;
    }

    public FieldID getFieldID_corner_threshold() {
        return createFieldID(mExportVarIdx_corner_threshold, null);
    }

    private final static int mExportVarIdx_bitmap = 1;
    private Allocation mExportVar_bitmap;
    public synchronized void set_bitmap(Allocation v) {
        setVar(mExportVarIdx_bitmap, v);
        mExportVar_bitmap = v;
    }

    public Allocation get_bitmap() {
        return mExportVar_bitmap;
    }

    public FieldID getFieldID_bitmap() {
        return createFieldID(mExportVarIdx_bitmap, null);
    }

    private final static int mExportVarIdx_bitmap_Ix = 2;
    private Allocation mExportVar_bitmap_Ix;
    public synchronized void set_bitmap_Ix(Allocation v) {
        setVar(mExportVarIdx_bitmap_Ix, v);
        mExportVar_bitmap_Ix = v;
    }

    public Allocation get_bitmap_Ix() {
        return mExportVar_bitmap_Ix;
    }

    public FieldID getFieldID_bitmap_Ix() {
        return createFieldID(mExportVarIdx_bitmap_Ix, null);
    }

    private final static int mExportVarIdx_bitmap_Iy = 3;
    private Allocation mExportVar_bitmap_Iy;
    public synchronized void set_bitmap_Iy(Allocation v) {
        setVar(mExportVarIdx_bitmap_Iy, v);
        mExportVar_bitmap_Iy = v;
    }

    public Allocation get_bitmap_Iy() {
        return mExportVar_bitmap_Iy;
    }

    public FieldID getFieldID_bitmap_Iy() {
        return createFieldID(mExportVarIdx_bitmap_Iy, null);
    }

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_create_greyscale = 1;
    public KernelID getKernelID_create_greyscale() {
        return createKernelID(mExportForEachIdx_create_greyscale, 59, null, null);
    }

    public void forEach_create_greyscale(Allocation ain, Allocation aout) {
        forEach_create_greyscale(ain, aout, null);
    }

    public void forEach_create_greyscale(Allocation ain, Allocation aout, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__U8)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        Type t0, t1;        // Verify dimensions
        t0 = ain.getType();
        t1 = aout.getType();
        if ((t0.getCount() != t1.getCount()) ||
            (t0.getX() != t1.getX()) ||
            (t0.getY() != t1.getY()) ||
            (t0.getZ() != t1.getZ()) ||
            (t0.hasFaces()   != t1.hasFaces()) ||
            (t0.hasMipmaps() != t1.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }

        forEach(mExportForEachIdx_create_greyscale, ain, aout, null, sc);
    }

    private final static int mExportForEachIdx_compute_derivatives = 2;
    public KernelID getKernelID_compute_derivatives() {
        return createKernelID(mExportForEachIdx_compute_derivatives, 57, null, null);
    }

    public void forEach_compute_derivatives(Allocation ain) {
        forEach_compute_derivatives(ain, null);
    }

    public void forEach_compute_derivatives(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(mExportForEachIdx_compute_derivatives, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_corner_detector = 3;
    public KernelID getKernelID_corner_detector() {
        return createKernelID(mExportForEachIdx_corner_detector, 59, null, null);
    }

    public void forEach_corner_detector(Allocation ain, Allocation aout) {
        forEach_corner_detector(ain, aout, null);
    }

    public void forEach_corner_detector(Allocation ain, Allocation aout, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__F32)) {
            throw new RSRuntimeException("Type mismatch with F32!");
        }
        Type t0, t1;        // Verify dimensions
        t0 = ain.getType();
        t1 = aout.getType();
        if ((t0.getCount() != t1.getCount()) ||
            (t0.getX() != t1.getX()) ||
            (t0.getY() != t1.getY()) ||
            (t0.getZ() != t1.getZ()) ||
            (t0.hasFaces()   != t1.hasFaces()) ||
            (t0.hasMipmaps() != t1.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }

        forEach(mExportForEachIdx_corner_detector, ain, aout, null, sc);
    }

    private final static int mExportForEachIdx_local_maximum = 4;
    public KernelID getKernelID_local_maximum() {
        return createKernelID(mExportForEachIdx_local_maximum, 59, null, null);
    }

    public void forEach_local_maximum(Allocation ain, Allocation aout) {
        forEach_local_maximum(ain, aout, null);
    }

    public void forEach_local_maximum(Allocation ain, Allocation aout, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__F32)) {
            throw new RSRuntimeException("Type mismatch with F32!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__U8)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        Type t0, t1;        // Verify dimensions
        t0 = ain.getType();
        t1 = aout.getType();
        if ((t0.getCount() != t1.getCount()) ||
            (t0.getX() != t1.getX()) ||
            (t0.getY() != t1.getY()) ||
            (t0.getZ() != t1.getZ()) ||
            (t0.hasFaces()   != t1.hasFaces()) ||
            (t0.hasMipmaps() != t1.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }

        forEach(mExportForEachIdx_local_maximum, ain, aout, null, sc);
    }

}

