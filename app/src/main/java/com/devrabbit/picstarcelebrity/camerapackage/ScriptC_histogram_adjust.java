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
public class ScriptC_histogram_adjust extends ScriptC {
    private static final String __rs_resource_name = "histogram_adjust";
    // Constructor
    public  ScriptC_histogram_adjust(RenderScript rs) {
        super(rs,
              __rs_resource_name,
              histogram_adjustBitCode.getBitCode32(),
              histogram_adjustBitCode.getBitCode64());
        __ALLOCATION = Element.ALLOCATION(rs);
        mExportVar_hdr_alpha = 0.5f;
        __F32 = Element.F32(rs);
        mExportVar_n_tiles = 0;
        __I32 = Element.I32(rs);
        mExportVar_width = 0;
        mExportVar_height = 0;
        __U8_4 = Element.U8_4(rs);
    }

    private Element __ALLOCATION;
    private Element __F32;
    private Element __I32;
    private Element __U8_4;
    private FieldPacker __rs_fp_ALLOCATION;
    private FieldPacker __rs_fp_F32;
    private FieldPacker __rs_fp_I32;
    private final static int mExportVarIdx_c_histogram = 0;
    private Allocation mExportVar_c_histogram;
    public synchronized void set_c_histogram(Allocation v) {
        setVar(mExportVarIdx_c_histogram, v);
        mExportVar_c_histogram = v;
    }

    public Allocation get_c_histogram() {
        return mExportVar_c_histogram;
    }

    public FieldID getFieldID_c_histogram() {
        return createFieldID(mExportVarIdx_c_histogram, null);
    }

    private final static int mExportVarIdx_hdr_alpha = 1;
    private float mExportVar_hdr_alpha;
    public synchronized void set_hdr_alpha(float v) {
        setVar(mExportVarIdx_hdr_alpha, v);
        mExportVar_hdr_alpha = v;
    }

    public float get_hdr_alpha() {
        return mExportVar_hdr_alpha;
    }

    public FieldID getFieldID_hdr_alpha() {
        return createFieldID(mExportVarIdx_hdr_alpha, null);
    }

    private final static int mExportVarIdx_n_tiles = 2;
    private int mExportVar_n_tiles;
    public synchronized void set_n_tiles(int v) {
        setVar(mExportVarIdx_n_tiles, v);
        mExportVar_n_tiles = v;
    }

    public int get_n_tiles() {
        return mExportVar_n_tiles;
    }

    public FieldID getFieldID_n_tiles() {
        return createFieldID(mExportVarIdx_n_tiles, null);
    }

    private final static int mExportVarIdx_width = 3;
    private int mExportVar_width;
    public synchronized void set_width(int v) {
        setVar(mExportVarIdx_width, v);
        mExportVar_width = v;
    }

    public int get_width() {
        return mExportVar_width;
    }

    public FieldID getFieldID_width() {
        return createFieldID(mExportVarIdx_width, null);
    }

    private final static int mExportVarIdx_height = 4;
    private int mExportVar_height;
    public synchronized void set_height(int v) {
        setVar(mExportVarIdx_height, v);
        mExportVar_height = v;
    }

    public int get_height() {
        return mExportVar_height;
    }

    public FieldID getFieldID_height() {
        return createFieldID(mExportVarIdx_height, null);
    }

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_histogram_adjust = 1;
    public KernelID getKernelID_histogram_adjust() {
        return createKernelID(mExportForEachIdx_histogram_adjust, 59, null, null);
    }

    public void forEach_histogram_adjust(Allocation ain, Allocation aout) {
        forEach_histogram_adjust(ain, aout, null);
    }

    public void forEach_histogram_adjust(Allocation ain, Allocation aout, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
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

        forEach(mExportForEachIdx_histogram_adjust, ain, aout, null, sc);
    }

}

