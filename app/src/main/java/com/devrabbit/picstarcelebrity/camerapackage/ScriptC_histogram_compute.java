package com.devrabbit.picstarcelebrity.camerapackage;

import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.renderscript.Type;

import androidx.annotation.RequiresApi;

public class ScriptC_histogram_compute extends ScriptC {
    private static final String __rs_resource_name = "histogram_compute";
    // Constructor
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  ScriptC_histogram_compute(RenderScript rs) {
        super(rs,
              __rs_resource_name,
              histogram_computeBitCode.getBitCode32(),
              histogram_computeBitCode.getBitCode64());
        mExportVar_zebra_stripes_threshold = 255;
        __I32 = Element.I32(rs);
        mExportVar_zebra_stripes_foreground_r = 0;
        mExportVar_zebra_stripes_foreground_g = 0;
        mExportVar_zebra_stripes_foreground_b = 0;
        mExportVar_zebra_stripes_foreground_a = 255;
        mExportVar_zebra_stripes_background_r = 255;
        mExportVar_zebra_stripes_background_g = 255;
        mExportVar_zebra_stripes_background_b = 255;
        mExportVar_zebra_stripes_background_a = 255;
        mExportVar_zebra_stripes_width = 40;
        __ALLOCATION = Element.ALLOCATION(rs);
        __U8_4 = Element.U8_4(rs);
        __F32_3 = Element.F32_3(rs);
    }

    private Element __ALLOCATION;
    private Element __F32_3;
    private Element __I32;
    private Element __U8_4;
    private FieldPacker __rs_fp_ALLOCATION;
    private FieldPacker __rs_fp_I32;
    private final static int mExportVarIdx_histogram = 0;
    private Allocation mExportVar_histogram;
    public void bind_histogram(Allocation v) {
        mExportVar_histogram = v;
        if (v == null) bindAllocation(null, mExportVarIdx_histogram);
        else bindAllocation(v, mExportVarIdx_histogram);
    }

    public Allocation get_histogram() {
        return mExportVar_histogram;
    }

    private final static int mExportVarIdx_histogram_r = 1;
    private Allocation mExportVar_histogram_r;
    public void bind_histogram_r(Allocation v) {
        mExportVar_histogram_r = v;
        if (v == null) bindAllocation(null, mExportVarIdx_histogram_r);
        else bindAllocation(v, mExportVarIdx_histogram_r);
    }

    public Allocation get_histogram_r() {
        return mExportVar_histogram_r;
    }

    private final static int mExportVarIdx_histogram_g = 2;
    private Allocation mExportVar_histogram_g;
    public void bind_histogram_g(Allocation v) {
        mExportVar_histogram_g = v;
        if (v == null) bindAllocation(null, mExportVarIdx_histogram_g);
        else bindAllocation(v, mExportVarIdx_histogram_g);
    }

    public Allocation get_histogram_g() {
        return mExportVar_histogram_g;
    }

    private final static int mExportVarIdx_histogram_b = 3;
    private Allocation mExportVar_histogram_b;
    public void bind_histogram_b(Allocation v) {
        mExportVar_histogram_b = v;
        if (v == null) bindAllocation(null, mExportVarIdx_histogram_b);
        else bindAllocation(v, mExportVarIdx_histogram_b);
    }

    public Allocation get_histogram_b() {
        return mExportVar_histogram_b;
    }

    private final static int mExportVarIdx_zebra_stripes_threshold = 4;
    private int mExportVar_zebra_stripes_threshold;
    public synchronized void set_zebra_stripes_threshold(int v) {
        setVar(mExportVarIdx_zebra_stripes_threshold, v);
        mExportVar_zebra_stripes_threshold = v;
    }

    public int get_zebra_stripes_threshold() {
        return mExportVar_zebra_stripes_threshold;
    }

    public FieldID getFieldID_zebra_stripes_threshold() {
        return createFieldID(mExportVarIdx_zebra_stripes_threshold, null);
    }

    private final static int mExportVarIdx_zebra_stripes_foreground_r = 5;
    private int mExportVar_zebra_stripes_foreground_r;
    public synchronized void set_zebra_stripes_foreground_r(int v) {
        setVar(mExportVarIdx_zebra_stripes_foreground_r, v);
        mExportVar_zebra_stripes_foreground_r = v;
    }

    public int get_zebra_stripes_foreground_r() {
        return mExportVar_zebra_stripes_foreground_r;
    }

    public FieldID getFieldID_zebra_stripes_foreground_r() {
        return createFieldID(mExportVarIdx_zebra_stripes_foreground_r, null);
    }

    private final static int mExportVarIdx_zebra_stripes_foreground_g = 6;
    private int mExportVar_zebra_stripes_foreground_g;
    public synchronized void set_zebra_stripes_foreground_g(int v) {
        setVar(mExportVarIdx_zebra_stripes_foreground_g, v);
        mExportVar_zebra_stripes_foreground_g = v;
    }

    public int get_zebra_stripes_foreground_g() {
        return mExportVar_zebra_stripes_foreground_g;
    }

    public FieldID getFieldID_zebra_stripes_foreground_g() {
        return createFieldID(mExportVarIdx_zebra_stripes_foreground_g, null);
    }

    private final static int mExportVarIdx_zebra_stripes_foreground_b = 7;
    private int mExportVar_zebra_stripes_foreground_b;
    public synchronized void set_zebra_stripes_foreground_b(int v) {
        setVar(mExportVarIdx_zebra_stripes_foreground_b, v);
        mExportVar_zebra_stripes_foreground_b = v;
    }

    public int get_zebra_stripes_foreground_b() {
        return mExportVar_zebra_stripes_foreground_b;
    }

    public FieldID getFieldID_zebra_stripes_foreground_b() {
        return createFieldID(mExportVarIdx_zebra_stripes_foreground_b, null);
    }

    private final static int mExportVarIdx_zebra_stripes_foreground_a = 8;
    private int mExportVar_zebra_stripes_foreground_a;
    public synchronized void set_zebra_stripes_foreground_a(int v) {
        setVar(mExportVarIdx_zebra_stripes_foreground_a, v);
        mExportVar_zebra_stripes_foreground_a = v;
    }

    public int get_zebra_stripes_foreground_a() {
        return mExportVar_zebra_stripes_foreground_a;
    }

    public FieldID getFieldID_zebra_stripes_foreground_a() {
        return createFieldID(mExportVarIdx_zebra_stripes_foreground_a, null);
    }

    private final static int mExportVarIdx_zebra_stripes_background_r = 9;
    private int mExportVar_zebra_stripes_background_r;
    public synchronized void set_zebra_stripes_background_r(int v) {
        setVar(mExportVarIdx_zebra_stripes_background_r, v);
        mExportVar_zebra_stripes_background_r = v;
    }

    public int get_zebra_stripes_background_r() {
        return mExportVar_zebra_stripes_background_r;
    }

    public FieldID getFieldID_zebra_stripes_background_r() {
        return createFieldID(mExportVarIdx_zebra_stripes_background_r, null);
    }

    private final static int mExportVarIdx_zebra_stripes_background_g = 10;
    private int mExportVar_zebra_stripes_background_g;
    public synchronized void set_zebra_stripes_background_g(int v) {
        setVar(mExportVarIdx_zebra_stripes_background_g, v);
        mExportVar_zebra_stripes_background_g = v;
    }

    public int get_zebra_stripes_background_g() {
        return mExportVar_zebra_stripes_background_g;
    }

    public FieldID getFieldID_zebra_stripes_background_g() {
        return createFieldID(mExportVarIdx_zebra_stripes_background_g, null);
    }

    private final static int mExportVarIdx_zebra_stripes_background_b = 11;
    private int mExportVar_zebra_stripes_background_b;
    public synchronized void set_zebra_stripes_background_b(int v) {
        setVar(mExportVarIdx_zebra_stripes_background_b, v);
        mExportVar_zebra_stripes_background_b = v;
    }

    public int get_zebra_stripes_background_b() {
        return mExportVar_zebra_stripes_background_b;
    }

    public FieldID getFieldID_zebra_stripes_background_b() {
        return createFieldID(mExportVarIdx_zebra_stripes_background_b, null);
    }

    private final static int mExportVarIdx_zebra_stripes_background_a = 12;
    private int mExportVar_zebra_stripes_background_a;
    public synchronized void set_zebra_stripes_background_a(int v) {
        setVar(mExportVarIdx_zebra_stripes_background_a, v);
        mExportVar_zebra_stripes_background_a = v;
    }

    public int get_zebra_stripes_background_a() {
        return mExportVar_zebra_stripes_background_a;
    }

    public FieldID getFieldID_zebra_stripes_background_a() {
        return createFieldID(mExportVarIdx_zebra_stripes_background_a, null);
    }

    private final static int mExportVarIdx_zebra_stripes_width = 13;
    private int mExportVar_zebra_stripes_width;
    public synchronized void set_zebra_stripes_width(int v) {
        setVar(mExportVarIdx_zebra_stripes_width, v);
        mExportVar_zebra_stripes_width = v;
    }

    public int get_zebra_stripes_width() {
        return mExportVar_zebra_stripes_width;
    }

    public FieldID getFieldID_zebra_stripes_width() {
        return createFieldID(mExportVarIdx_zebra_stripes_width, null);
    }

    private final static int mExportVarIdx_bitmap = 14;
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

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_histogram_compute_by_luminance = 1;
    public KernelID getKernelID_histogram_compute_by_luminance() {
        return createKernelID(mExportForEachIdx_histogram_compute_by_luminance, 57, null, null);
    }

    public void forEach_histogram_compute_by_luminance(Allocation ain) {
        forEach_histogram_compute_by_luminance(ain, null);
    }

    public void forEach_histogram_compute_by_luminance(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        forEach(mExportForEachIdx_histogram_compute_by_luminance, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_histogram_compute_by_value = 2;
    public KernelID getKernelID_histogram_compute_by_value() {
        return createKernelID(mExportForEachIdx_histogram_compute_by_value, 57, null, null);
    }

    public void forEach_histogram_compute_by_value(Allocation ain) {
        forEach_histogram_compute_by_value(ain, null);
    }

    public void forEach_histogram_compute_by_value(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            forEach(mExportForEachIdx_histogram_compute_by_value, ain, null, null, sc);
        }
    }

    private final static int mExportForEachIdx_histogram_compute_by_value_f = 3;
    public KernelID getKernelID_histogram_compute_by_value_f() {
        return createKernelID(mExportForEachIdx_histogram_compute_by_value_f, 57, null, null);
    }

    public void forEach_histogram_compute_by_value_f(Allocation ain) {
        forEach_histogram_compute_by_value_f(ain, null);
    }

    public void forEach_histogram_compute_by_value_f(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__F32_3)) {
            throw new RSRuntimeException("Type mismatch with F32_3!");
        }
        forEach(mExportForEachIdx_histogram_compute_by_value_f, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_histogram_compute_by_intensity = 4;
    public KernelID getKernelID_histogram_compute_by_intensity() {
        return createKernelID(mExportForEachIdx_histogram_compute_by_intensity, 57, null, null);
    }

    public void forEach_histogram_compute_by_intensity(Allocation ain) {
        forEach_histogram_compute_by_intensity(ain, null);
    }

    public void forEach_histogram_compute_by_intensity(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        forEach(mExportForEachIdx_histogram_compute_by_intensity, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_histogram_compute_by_intensity_f = 5;
    public KernelID getKernelID_histogram_compute_by_intensity_f() {
        return createKernelID(mExportForEachIdx_histogram_compute_by_intensity_f, 57, null, null);
    }

    public void forEach_histogram_compute_by_intensity_f(Allocation ain) {
        forEach_histogram_compute_by_intensity_f(ain, null);
    }

    public void forEach_histogram_compute_by_intensity_f(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__F32_3)) {
            throw new RSRuntimeException("Type mismatch with F32_3!");
        }
        forEach(mExportForEachIdx_histogram_compute_by_intensity_f, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_histogram_compute_by_lightness = 6;
    public KernelID getKernelID_histogram_compute_by_lightness() {
        return createKernelID(mExportForEachIdx_histogram_compute_by_lightness, 57, null, null);
    }

    public void forEach_histogram_compute_by_lightness(Allocation ain) {
        forEach_histogram_compute_by_lightness(ain, null);
    }

    public void forEach_histogram_compute_by_lightness(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        forEach(mExportForEachIdx_histogram_compute_by_lightness, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_histogram_compute_rgb = 7;
    public KernelID getKernelID_histogram_compute_rgb() {
        return createKernelID(mExportForEachIdx_histogram_compute_rgb, 57, null, null);
    }

    public void forEach_histogram_compute_rgb(Allocation ain) {
        forEach_histogram_compute_rgb(ain, null);
    }

    public void forEach_histogram_compute_rgb(Allocation ain, LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        forEach(mExportForEachIdx_histogram_compute_rgb, ain, null, null, sc);
    }

    private final static int mExportForEachIdx_generate_zebra_stripes = 8;
    public KernelID getKernelID_generate_zebra_stripes() {
        return createKernelID(mExportForEachIdx_generate_zebra_stripes, 59, null, null);
    }

    public void forEach_generate_zebra_stripes(Allocation ain, Allocation aout) {
        forEach_generate_zebra_stripes(ain, aout, null);
    }

    public void forEach_generate_zebra_stripes(Allocation ain, Allocation aout, LaunchOptions sc) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            forEach(mExportForEachIdx_generate_zebra_stripes, ain, aout, null, sc);
        }
    }

    private final static int mExportForEachIdx_generate_focus_peaking = 9;
    public KernelID getKernelID_generate_focus_peaking() {
        return createKernelID(mExportForEachIdx_generate_focus_peaking, 59, null, null);
    }

    public void forEach_generate_focus_peaking(Allocation ain, Allocation aout) {
        forEach_generate_focus_peaking(ain, aout, null);
    }

    public void forEach_generate_focus_peaking(Allocation ain, Allocation aout, LaunchOptions sc) {
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

        forEach(mExportForEachIdx_generate_focus_peaking, ain, aout, null, sc);
    }

    private final static int mExportForEachIdx_generate_focus_peaking_filtered = 10;
    public KernelID getKernelID_generate_focus_peaking_filtered() {
        return createKernelID(mExportForEachIdx_generate_focus_peaking_filtered, 59, null, null);
    }

    public void forEach_generate_focus_peaking_filtered(Allocation ain, Allocation aout) {
        forEach_generate_focus_peaking_filtered(ain, aout, null);
    }

    public void forEach_generate_focus_peaking_filtered(Allocation ain, Allocation aout, LaunchOptions sc) {
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

        forEach(mExportForEachIdx_generate_focus_peaking_filtered, ain, aout, null, sc);
    }

    private final static int mExportFuncIdx_init_histogram = 0;
    public void invoke_init_histogram() {
        invoke(mExportFuncIdx_init_histogram);
    }

    private final static int mExportFuncIdx_init_histogram_rgb = 1;
    public void invoke_init_histogram_rgb() {
        invoke(mExportFuncIdx_init_histogram_rgb);
    }

}

