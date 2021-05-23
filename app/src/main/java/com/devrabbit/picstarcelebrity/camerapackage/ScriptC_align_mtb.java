package com.devrabbit.picstarcelebrity.camerapackage;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

/**
 * @hide
 */
public class ScriptC_align_mtb extends ScriptC {
    private static final String __rs_resource_name = "align_mtb";
    // Constructor
    public  ScriptC_align_mtb(RenderScript rs) {
        super(rs,
              __rs_resource_name,
              align_mtbBitCode.getBitCode32(),
              align_mtbBitCode.getBitCode64());
        __ALLOCATION = Element.ALLOCATION(rs);
        mExportVar_step_size = 1;
        __I32 = Element.I32(rs);
        mExportVar_off_x = 0;
        mExportVar_off_y = 0;
        __U8 = Element.U8(rs);
    }

    private Element __ALLOCATION;
    private Element __I32;
    private Element __U8;
    private FieldPacker __rs_fp_ALLOCATION;
    private FieldPacker __rs_fp_I32;
    private final static int mExportVarIdx_bitmap0 = 0;
    private Allocation mExportVar_bitmap0;
    public synchronized void set_bitmap0(Allocation v) {
        setVar(mExportVarIdx_bitmap0, v);
        mExportVar_bitmap0 = v;
    }

    public Allocation get_bitmap0() {
        return mExportVar_bitmap0;
    }

    public FieldID getFieldID_bitmap0() {
        return createFieldID(mExportVarIdx_bitmap0, null);
    }

    private final static int mExportVarIdx_bitmap1 = 1;
    private Allocation mExportVar_bitmap1;
    public synchronized void set_bitmap1(Allocation v) {
        setVar(mExportVarIdx_bitmap1, v);
        mExportVar_bitmap1 = v;
    }

    public Allocation get_bitmap1() {
        return mExportVar_bitmap1;
    }

    public FieldID getFieldID_bitmap1() {
        return createFieldID(mExportVarIdx_bitmap1, null);
    }

    private final static int mExportVarIdx_step_size = 2;
    private int mExportVar_step_size;
    public synchronized void set_step_size(int v) {
        setVar(mExportVarIdx_step_size, v);
        mExportVar_step_size = v;
    }

    public int get_step_size() {
        return mExportVar_step_size;
    }

    public FieldID getFieldID_step_size() {
        return createFieldID(mExportVarIdx_step_size, null);
    }

    private final static int mExportVarIdx_off_x = 3;
    private int mExportVar_off_x;
    public synchronized void set_off_x(int v) {
        setVar(mExportVarIdx_off_x, v);
        mExportVar_off_x = v;
    }

    public int get_off_x() {
        return mExportVar_off_x;
    }

    public FieldID getFieldID_off_x() {
        return createFieldID(mExportVarIdx_off_x, null);
    }

    private final static int mExportVarIdx_off_y = 4;
    private int mExportVar_off_y;
    public synchronized void set_off_y(int v) {
        setVar(mExportVarIdx_off_y, v);
        mExportVar_off_y = v;
    }

    public int get_off_y() {
        return mExportVar_off_y;
    }

    public FieldID getFieldID_off_y() {
        return createFieldID(mExportVarIdx_off_y, null);
    }

    private final static int mExportVarIdx_errors = 5;
    private Allocation mExportVar_errors;
    public void bind_errors(Allocation v) {
        mExportVar_errors = v;
        if (v == null) bindAllocation(null, mExportVarIdx_errors);
        else bindAllocation(v, mExportVarIdx_errors);
    }

    public Allocation get_errors() {
        return mExportVar_errors;
    }

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_align_mtb = 1;
    public KernelID getKernelID_align_mtb() {
        return createKernelID(mExportForEachIdx_align_mtb, 57, null, null);
    }

    public void forEach_align_mtb(Allocation ain) {
        forEach_align_mtb(ain, null);
    }

    public void forEach_align_mtb(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(mExportForEachIdx_align_mtb, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_align = 2;
    public KernelID getKernelID_align() {
        return createKernelID(mExportForEachIdx_align, 57, null, null);
    }

    public void forEach_align(Allocation ain) {
        forEach_align(ain, null);
    }

    public void forEach_align(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(mExportForEachIdx_align, ain, null, null, sc);
    }

    private final static int mExportFuncIdx_init_errors = 0;
    public void invoke_init_errors() {
        invoke(mExportFuncIdx_init_errors);
    }

}

