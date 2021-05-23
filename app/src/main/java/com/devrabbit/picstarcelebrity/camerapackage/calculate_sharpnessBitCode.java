package com.devrabbit.picstarcelebrity.camerapackage;

public class calculate_sharpnessBitCode {
    // return byte array representation of the 32-bit bitcode.
    public static byte[] getBitCode32() {
        return getBitCode32Internal();
    };

    private static byte[] getSegment32_0() {
        byte[] data = {
             -34,  -64,   23,   11,    0,    0,    0,    0,   44,    0,    0,    0,   80,    9,    0,    0,
               0,    0,    0,    0,   21,    0,    0,    0,    0,    0,    0,    0,    1,   64,    4,    0,
             106,    9,    0,    0,    2,   64,    4,    0,    3,    0,    0,    0,   66,   67,  -64,  -34,
              33,   12,    0,    0,   81,    2,    0,    0,    1,   16,    0,    0,   18,    0,    0,    0,
               7, -127,   35, -111,   65,  -56,    4,   73,    6,   16,   50,   57, -110,    1, -124,   12,
              37,    5,    8,   25,   30,    4, -117,   98, -128,   20,   69,    2,   66, -110,   11,   66,
             -92,   16,   50,   20,   56,    8,   24,   73,   10,   50,   68,   36,   72,   10, -112,   33,
              35,  -60,   82, -128,   12,   25,   33,  114,   36,    7,  -56,   72,   17,   98,  -88,  -96,
             -88,   64,  -58,  -16,    1,    0,    0,    0,   73,   24,    0,    0,   16,    0,    0,    0,
              11, -124,   -1,   -1,   -1,   -1,   31,  -64,   96, -127,  -16,   -1,   -1,   -1,   -1,    3,
              24,   44,   16,   -2,   -1,   -1,   -1,  127,    0,   22,    8,   -1,   -1,   -1,   -1,   63,
            -128,  -47,    2,  -31,   -1,   -1,   -1,   -1,    7,   48,   88,   32,   -4,   -1,   -1,   -1,
              -1,    0,   70,   11, -120,    0,   -8,   -1,   -1,   -1,   -1,    1,   12,    0,    0,    0,
            -119,   32,    0,    0,   27,    0,    0,    0,   50,   34,   72,    9,   32,  100, -123,    4,
            -109,   34,  -92, -124,    4, -109,   34,  -29, -124,  -95, -112,   20,   18,   76, -118, -116,
              11, -124,  -92,   76,   16,  108,  115,    4,   96,   64, -128,  -62,   89,  -46,   20,   81,
             -62,  -28,   71,  -46,   15,   44, -117,   35,    0,   19,  -30,   52,  126, -115, -126,   64,
              99,    4,  -96,    4, -123,  -52,   28,    1,   50, -116,   64,   32, -123,   40,    9,    0,
              82,   25,    0,   10, -111,   24,    0,  -44, -118,    0,   24,  -67,   34,    0,   64,  -79,
              12,   32,    0, -102,   69,   40, -124,  -22,   64,  -64,   28,    1,   40,  -52,   17,    4,
              83,    0,    0,    0,   19,  -80,  112, -112, -121,  118,  -80, -121,   59,  104,    3,  119,
             120,    7,  119,   40, -121,   54,   96, -121,  116,  112, -121,  122,  -64, -121,   54,   56,
               7,  119,  -88, -121,  114,    8,    7,  113,   72, -121,   13,  100,   80,   14,  109,    0,
              15,  122,   48,    7,  114,  -96,    7,  115,   32,    7,  109, -112,   14,  118,   64,    7,
             122,   96,    7,  116,  -48,    6,  -10,   16,    7,  114, -128,    7,  122,   96,    7,  116,
             -96,    7,  113,   32,    7,  120,  -48,    6,  -18,   48,    7,  114,  -48,    6,  -77,   96,
               7,  116,  -96,  -13,   64, -122,    4,   50,   66,   68,    4,  -40,  -95,    4,    0, -125,
              12,    0,    0,    4,  -64,   14,   69,    0,   34,  100,    0,    0,   32,    0,   72,   16,
              62,   68,   57,    0,    0,    8,    0,    0,    0, -126,   33,  -54,    2,    0, -128,    0,
               0,    0,   16,   12,   81,   28,   32,    0,    6,    0,    0, -128,   96, -120,    2,    1,
               1,   48,    0,    0,    0,    4,   67,   20,    9,    8,    0,    2,    0,    0,   32,   24,
             -94,   80,   64,    0,   12,    0,    0,    0,  -63,   16,  -27,    0,    0,   64,    0,    0,
               0,    8, -122,   40,   22,   16,    0,    3,    0,    0,   64, -112,    5,    2,    0,    0,
               8,    0,    0,    0,   50,   30, -104,   20,   25,   17,   76, -112, -116,    9,   38,   71,
             -58,    4,   67,    2,   35,    0,   37,   80,    8, -124,   71,    0,  104, -116,    0,   80,
              24,    1,    0,    0,  121,   24,    0,    0, -116,    0,    0,    0,   26,    3,   76, -112,
              70,    2,   19,   52,   68,    0,   24,   42,  119,   99,  104,   97,  114,   95,  115,  105,
             122,  101,   67,    4,  -96,   25,   98,    0,  -52,   21,    0,   13, -101,  -74,   52,  -73,
             -81,   50,  -73,  -70,  -74,  -81,  -71,   52,  -67,  -78,   33,    6,  -64,   92,    4,  -48,
            -112,   15, -126,  -36,  -56,  -28,  -34,  -46,  -56,   64,  -58,  -40,  -62,  -36,  -50,   64,
             -20,  -54,  -28,  -26,  -46,  -34,  -36,   64,  102,   92,  112,   92,  100,  110,  106,  104,
             112,   96,   64,   64,   80,  -60,  -62,  -26,  -54,  -56,   64,  -34,  -36,   64, -104, -104,
             -84, -102,   64,  102,   92,  112,   92,  100,  110,  106,  104,  112,   96,   82, -122,    8,
            -105,  -63,  -61,  -82,   76,  110,   46,  -19,  -51, -115,   65,  -52,   16,  -30,   66,  -82,
            -124, -111,   90, -104,   93,  -40,   23,   92,  -40,  -40,   90,  -40,   89,  -39, -105,   91,
              88,   91,   25,   53,  -73,   50,   58, -105,  -71,  -73,   58,  -71,  -79,   50,  -77,   55,
             -71,  -77,   50, -105,   55,  -72,   50,  -73,  -79,  -80,  -74,   50,  -71,  -80,   33,  -60,
             -75,   92,   12,   27,  -71,  -71,   47,   51,  -72,   47,  -71,   50,  -74,   48,  -68,   50,
              50,    2,   67, -120,  -53,  -71,   30,   26,   98,  105,  116,  109,   97,  112,   20,  100,
              96, -122,   16,   87,  116,   73,   36,  -26,  -22,  -38,  -26,   72,  -88,  -92,  -71,  -47,
              13,   33,   46,  -22,  -86,   88,  -36,  -91, -111,  -47,  -95,   49,  -88,   25,   66,   92,
             -41, -123,   49, -128,   25,   34,   92,   26, -109,   52,  -73,   52,  -70,  -81,  -71,  -70,
             -74,  -71,   33,  -62,  -59, -111, -112,  123,  123,  -93,   27,   34,   92,   30,  -89,  -79,
              48,  -74,  -79,   58,  -74,   48,  -70,  -78,  -81,   57,  -76,   48,   57,   56,  -73,  -78,
             -71,  -71,   33,  -62,    5,    6,   20,  106,  110, -122,    8, -105,   24,  112,   72,  115,
             -93,  -29,  -13,  -42,  -26, -106,    6,   -9,   70,   87,  -26,   70,    7,   50, -122,   22,
              38,  -57,  104,   42,  -83,   13, -114,  -83,   12,  100,  -24,  101,  104,  101,    5, -124,
              74,   40,   40,  104, -120,  112, -103,  -63,   16,    1,  115, -122,   24,   87,   25,   92,
             103, -128,   57,   67, -116, -117,   12,  -82,   52,  -64, -100,   33,  -58,  -91,    6, -105,
              26,   96,   14, -105,   48,  -73,   60,   16,  -72,  -73,   52,   55,  -70,   50,  -71,   33,
             -58,  -59,    6,   87,   26,   96,  -50,   16,  -29,  106, -125,  -85,   13,   48,  -89,   17,
              27, -101,   93, -101,   75,  -37,   27,   89,   29,   91, -103, -117,   25,   91,  -40,  -39,
             -36,   20,   97,   40,  -86,  -80,  -79,  -39,  -75,  -71,  -92, -111, -107,  -71,  -47,   77,
               9, -114,   30,   35,  112,  114,   97,  103,  109,   97,   83,    6,  -91, -127,  -22, -116,
             -56,  -51,  125, -107,  -31,  -63,  -67,  -55,  -47,  125,  -39, -123,  -55,   77,   25,   38,
              43,   43,   52,   34,   55,   -9,  -11,   38,  -90,   86,   54,   70,   -9,   53,  -57,  -10,
              70,   55,   55,   37,  -40,   -6, -116,  -56,  -51,  125, -107,  -31,  -63,  -67,  -55,  -47,
             125, -103,  -43,  -71, -115,   77,    9,  -70,   94,   35,  114,  115,   95,  101,  120,  112,
             111,  114,  116,   95,  102,  111,  114,  101,   97,   99,  104,   95,  110,   97,  109,  101,
              83, -124,   47,   12,   42, -115,  -56,  -51,  125, -107,  -31,  -63,  -67,  -55,  -47,  125,
            -103,  -67,  -55, -107, -123, -115,  -95,   77,   17,  -74,   49,    0,  121,   24,    0,    0,
              92,    0,    0,    0,   51,    8, -128,   28,  -60,  -31,   28,  102,   20,    1,   61, -120,
              67,   56, -124,  -61, -116,   66, -128,    7,  121,  120,    7,  115, -104,  113,   12,  -26,
               0,   15,  -19,   16,   14,  -12, -128,   14,   51,   12,   66,   30,  -62,  -63,   29,  -50,
             -95,   28,  102,   48,    5,   61, -120,   67,   56, -124, -125,   27,  -52,    3,   61,  -56,
              67,   61, -116,    3,   61,  -52,  120, -116,  116,  112,    7,  123,    8,    7,  121,   72,
            -121,  112,  112,    7,  122,  112,    3,  118,  120, -121,  112,   32, -121,   25,  -52,   17,
              14,  -20, -112,   14,  -31,   48,   15,  110,   48,   15,  -29,  -16,   14,  -16,   80,   14,
              51,   16,  -60,   29,  -34,   33,   28,  -40,   33,   29,  -62,   97,   30,  102,   48, -119,
              59,  -68, -125,   59,  -48,   67,   57,  -76,    3,   60,  -68, -125,   60, -124,    3,   59,
             -52,  -16,   20,  118,   96,    7,  123,  104,    7,   55,  104, -121,  114,  104,    7,   55,
            -128, -121,  112, -112, -121,  112,   96,    7,  118,   40,    7,  118,   -8,    5,  118,  120,
            -121,  119, -128, -121,   95,    8, -121,  113,   24, -121,  114, -104, -121,  121, -104, -127,
              44,  -18,  -16,   14,  -18,  -32,   14,  -11,  -64,   14,  -20,   48,    3,   98,  -56,  -95,
              28,  -28,  -95,   28,  -52,  -95,   28,  -28,  -95,   28,  -36,   97,   28,  -54,   33,   28,
             -60, -127,   29,  -54,   97,    6,  -42, -112,   67,   57,  -56,   67,   57, -104,   67,   57,
             -56,   67,   57,  -72,  -61,   56, -108,   67,   56, -120,    3,   59, -108,  -61,   47,  -68,
            -125,   60,   -4, -126,   59,  -44,    3,   59,  -80,  -61,   12,  -57,  105, -121,  112,   88,
            -121,  114,  112, -125,  116,  104,    7,  120,   96, -121,  116,   24, -121,  116,  -96, -121,
              25,  -50,   83,   15,  -18,    0,   15,  -14,   80,   14,  -28, -112,   14,  -29,   64,   15,
             -31,   32,   14,  -20,   80,   14,   51,   32,   40,   29,  -36,  -63,   30,  -62,   65,   30,
             -46,   33,   28,  -36, -127,   30,  -36,  -32,   28,  -28,  -31,   29,  -22,    1,   30,  102,
              24,   81,   56,  -80,   67,   58, -100, -125,   59,  -52,   80,   36,  118,   96,    7,  123,
             104,    7,   55,   96, -121,  119,  120,    7,  120, -104,   81,   76,  -12, -112,   15,  -16,
              80,   14,    0,    0,  113,   32,    0,    0,   43,    0,    0,    0,    6,   80,   88,  -56,
              48,   29,   38,   96,    4,  -56,  -60,    0, -113,   17,   76,    2,  -80,    8,  -44,    2,
              76,  -60,   47,   29,   64,  -12,   52, -124,   36,   89,    0,   33,   81, -116,  100,    5,
             106,  -16,  -49,  118,   29,   73,   32,   49,  121,   11,  -63,   16,  -51,  -92,   77,   63,
              37,   28,   64, -124,  -41,  119,   36,   -3,  -64,  -78,   56,    2,   48,   33,   78, -109,
              36,  118,  112,   -4,  -13,   13,    4,   18,   98,    6,  102,  -16,  -49,  117,   31,   73,
             -38,  -78,   56,    2,   48,   33,   78,    3,   18,   83, -121,   48,  114,  125,   71,  -46,
              15,   44, -117,   35,    0,   19,  -30,   52, -106, -128,   -8, -111,  -28,   15, -109,   19,
             -39,   64, -126,   52,  -56,  -12,   75,   20,   35,   25,  -62,  -12,  -49,  117,   29,   73,
             -38,  -28,   48, -120,  -96,   13,   67,   -6,   34, -120,   41, -124,  -63,   63,  -41,  119,
              36,  113,   11,    1,   68,  104, -112,   16,  -62, -108,  -42,  119,   36,   -3,  -64,  -78,
              56,    2,   48,   33,   78,    3,    0,    0,   97,   32,    0,    0,   34,    0,    0,    0,
              19,    4,    4,    1,   99,  -72,   33,   90,  -52,   96, -106,   33,   16,   36,   10,  -58,
              44,  -63, -128,    3,   21,  -64,   18,   88,  -61,  120,  -62,   68,   13,   70,   84,  -53,
               0,   24,  -59,  -64, -128, -128,   49,  -36,   96,   93,  104,   48,  -53,   48,    8,   88,
              56,   16,    0,    0,   12,    0,    0,    0, -122,   49,    8,  -52,  115,    1, -122,  -65,
              68,   -2,  115, -100,  -64,  -30,   -1,   66, -124,   76,   63,   49,   24, -124,   85,   32,
              64,   20,    1,   24,   50,   92,   38,   65,    8,  -52, -125, -101,  -59, -128,   52, -126,
              81,   16, -120,   79,  -25,    0,    0,    0,    1,   49,    0,    0,    5,    0,    0,    0,
              91,    6,   32,   88, -125,   45,  -61,   16,  -72,  -63, -106,    1,    9,  -42,   96,  -53,
             -96,    4,  107,    0,    0,    0,    0,    0,   97,   32,    0,    0, -115,    0,    0,    0,
              19,    4,   73,   44,   16,    0,    0,    0,    3,    0,    0,    0,    4,  106,  -96,    8,
              40,  -40,   18,    1,   98,   51,    0,    0,   99,    8,   17,  115,   24, -120,  -31,    6,
             105,    1, -125,   89,    6,   33,  -48,   74,  -94, -128,  -84,   49,  -37,  112,  113,  -64,
            -120, -127,   81,    4,   69,  -73,   77,   99,    8,   30,  115,   31, -120,   89,    2,   97,
             -96,    2,    0, -125,   32,    3,    8,   24,   38,    6,   20,    8, -122,   27,  -58,   64,
              18, -125,   89, -122, -127,   32, -125, -110,   24,   32,  107,  -52,   54,   92,  102,    0,
            -116,   24,   24,   69,   80, -100,   65,   25,   76,   99,    8,  104,  -64,   92,   26, -128,
            -104,   37,   32,    6,   42,    0,   53,   24,   50,   97,  -72,   97,   90,  -64,   96, -106,
             -63,   40,  -40,  -96,   38,   10,  -56,   26,  -77,   13, -105,   27,    0,   35,    6,   70,
              17,   20,  111,   32,  -75,  -63,   24,    2,   28,   48,   23,    7,   32,  102,    9, -116,
            -127,   10,   64,   14, -118,   48,   32,  -56,   26,  -77,   13,   23,   29,    0,   35,    6,
              68,   17,   24,  117,   80,  118,   64,  -63,  112,  -61,   29,   76,   98,   48,  -53,  112,
              32,  120,   80,   19,    3,  100, -115,  -39, -122,   75,   15, -128,   17,    3,  -93,    8,
            -118,   61, -112,  -14,   96,   12, -127,   15, -104,  -21,    3,   16,  -77,    4,  -56,   64,
               5,  -32,    7,  -57,   26,   24, -106,   85,   55,  -80, -116,  -71, -127,  -51,    1,   40,
            -124,  -64,   68,  -31,   15,   64,   96,  -93,   16,   10,   32,   40,   82,  104,  104,  -60,
            -128,   48, -126,  -93,   20,   40,   24,  -29,    9,  -89,   32, -115,   24,   20,   69, -128,
             -96, -126,   41,   96,   56,   16,    0,    0,   64,    0,    0,    0,  102,   99,    8,  -64,
             -78,  -44,  -71,  113,   36,   62,  -43,   60, -128,  -96,  -28,  -72, -123,   24,   11,   81,
              76,   62,  109,   51,  -58,   66,   20, -109,   95,  -37,    8,   33,   81,  -63,  109,   60,
            -122,    0,   44, -117,   77, -101,  -58,   32,   48, -113,   33,   33,   64,   20,    1,   24,
              50,   92,  102,  100,    8,  -64,  -78,  -40,  -68,   37,   25,    2,  -80,   44,  118,  111,
              56, -123,  -64,   60,  -11,  110,   27, -125,   68,    5,  -42,   82,   72,   84,   80,  -41,
               6,   52,   48,  -44,   98,   55, -123,   68,    5,  -75,  110,   40, -123,    0,   44, -117,
             110,   57,    5,   48,   12,  117,  111,   65,   70,    1,    8, -109,   19,   25,   72,   33,
              56,   77,  101, -101,   74,   33,   56,   77,  -59, -101,   80,    1,   12, -125,  -99,  -37,
              80,    1,   12, -125,  -83,   27,   81,   33,   81, -127,  -67,   27,    9,   33,   48,   15,
             110,   25, -124,  -32,   52, -107,  -79,   16,    2,  -13,  -12,   38,   99,    8,   78,   83,
             -43,  -72,  -83,   28,   17,   98,   28, -109,   79,  -37,  -49,   17,   33,  -58,   49,   -7,
             -75,   21,   13,    3,   82,   89,  -55,    0,   12, -125,  -47,   28,   62,  -43,   60, -128,
             -96, -104,   78,  -30,   83,  -51,    3,    8,   74,   94,   91, -124, -128,   52,   38,   17,
              92,   54,   17,   96,  -26,   99,    8,   78,   83,  -39,  -74, -103,   36,   62,  -43,   60,
            -128,  -96,  -28,  -73,  121,   16,    2,  -80,   44,  -26, -110,   -8,   84,  -13,    0, -126,
            -110,  -37,    6,   99,    8,  -64,  -78,  -44,   54,    0,    0,    0,    1,   49,    0,    0,
               3,    0,    0,    0,   91,    6,   38,   88, -125,   45,  -61,   29,    4,  110,    0,    0,
               0,    0,    0,    0,   97,   32,    0,    0,    3,    0,    0,    0,   19,    4,  -63, -120,
               1,  113,    4, -118, -128,    0,    0,    0,    0,    0,    0,    0,
        };
        return data;
    }

    private static int bitCode32Length = 2428;

    private static byte[] getBitCode32Internal() {
        byte[] bc = new byte[bitCode32Length];
        int offset = 0;
        byte[] seg;
        seg = getSegment32_0();
        System.arraycopy(seg, 0, bc, offset, seg.length);
        offset += seg.length;
        return bc;
    }

    // return byte array representation of the 64-bit bitcode.
    public static byte[] getBitCode64() {
        return getBitCode64Internal();
    };

    private static byte[] getSegment64_0() {
        byte[] data = {
             -34,  -64,   23,   11,    0,    0,    0,    0,   44,    0,    0,    0,    8,   10,    0,    0,
               0,    0,    0,    0,   21,    0,    0,    0,    0,    0,    0,    0,    1,   64,    4,    0,
             106,    9,    0,    0,    2,   64,    4,    0,    3,    0,    0,    0,   66,   67,  -64,  -34,
              33,   12,    0,    0,  127,    2,    0,    0,    1,   16,    0,    0,   18,    0,    0,    0,
               7, -127,   35, -111,   65,  -56,    4,   73,    6,   16,   50,   57, -110,    1, -124,   12,
              37,    5,    8,   25,   30,    4, -117,   98, -128,   20,   69,    2,   66, -110,   11,   66,
             -92,   16,   50,   20,   56,    8,   24,   73,   10,   50,   68,   36,   72,   10, -112,   33,
              35,  -60,   82, -128,   12,   25,   33,  114,   36,    7,  -56,   72,   17,   98,  -88,  -96,
             -88,   64,  -58,  -16,    1,    0,    0,    0,   73,   24,    0,    0,   21,    0,    0,    0,
              11, -124,   -1,   -1,   -1,   -1,   31,  -64,   96, -127,  -16,   -1,   -1,   -1,   -1,    3,
              24,   44,   16,   -2,   -1,   -1,   -1,  127,    0,   22,   24,    1,    8, -126,   32,    8,
              18,    2,    8, -126,   32,    8,   18,   -1,   -1,   -1,   -1,   63, -128,  -63,    2,  -31,
              -1,   -1,   -1,   -1,    7,   48,   90,   64,    4,  -64,   -1,   -1,   -1,   -1,   15,   96,
             -80,   64,   -8,   -1,   -1,   -1,   -1,    1, -116,   22,    8,   -1,   -1,   -1,   -1,   63,
            -128,    1,    0,    0, -119,   32,    0,    0,   29,    0,    0,    0,   50,   34,   72,    9,
              32,  100, -123,    4, -109,   34,  -92, -124,    4, -109,   34,  -29, -124,  -95, -112,   20,
              18,   76, -118, -116,   11, -124,  -92,   76,   16,  120,  115,    4,   96,   64, -128,  -62,
              28,    1,   40,  -48,  -72,   75, -102,   34,   74, -104,   -4,   72,   -6, -127,  101,  113,
               4,   96,   66, -100,  -58,  -81,  107,   68,   16,   66,    8, -107,   17, -128,   18,   28,
              66, -123,   56,    0,    0,   82,  115,    4,  -56,   48,    2,  -63,   20,  -94,   25,    0,
             -56,   17, -101,   35,    8, -118,  113,   32,   28,   32, -110,   44,    2,   48,   68, -117,
               0,    0,  -39,   50, -128,    0,    8,   23,  -31,   24,  -46,    3,    1,   83,    0,    0,
              19,  -76,  112,    8,    7,  121,   24,    7,  116,  -80,    3,   58,  104,    3,  119,  120,
               7,  119,   40, -121,   54,   96, -121,  116,  112, -121,  122,  -64, -121,   54,   56,    7,
             119,  -88, -121,  114,    8,    7,  113,   72, -121,   13,  115,   80,   14,  109,  -48,   14,
             122,   80,   14,  109, -112,   14,  120,  -96,    7,  120,  -96,    7,  115,   32,    7,  109,
            -112,   14,  113,   96,    7,  122,   16,    7,  118,  -96,    7,  115,   32,    7,  109, -112,
              14,  118,   64,    7,  122,   96,    7,  116,  -48,    6,  -23,   16,    7,  114, -128,    7,
             122,   16,    7,  114, -128,    7,  109,  -32,   14,  115,   32,    7,  122,   96,    7,  116,
             -48,    6,  -77,   16,    7,  114, -128,    7,   58,   15,  100,   72,   32,   35,   68,   70,
            -128,   29,   74,    0,   52,  -56,    0,    0,   64,    0,  -20,   80,    4,   64,   66,    8,
               0,    0,    2, -128,   54,   33, -121,   40,    9,    0,    0,    1,    0,    0,   64,   48,
              68,   89,    0,    0,   16,    0,    0,    0, -126,   33,  -54,    3,    4,  -64,    0,    0,
               0,   16,   12,   81,   38,   32,    0,    8,    0,    0,    0,   96, -120,   82,    1,    1,
              48,    0,    0,    0,    4,   67, -108,   11,    8, -128,    2,    0,    0,   32,   24,  -94,
             100,   64,    0,   12,    0,    0,    0,  -63,   16,   37,    1,    0,   64,    0,    0,    0,
               8, -122,   40,   27,   16,    0,    3,    0,    0,   64, -112,    5,    2,    0,    0,    0,
               9,    0,    0,    0,   50,   30, -104,   20,   25,   17,   76, -112, -116,    9,   38,   71,
             -58,    4,   67,    2,   35,    0,   52,   10,  102,    4,  -96,   32,   10, -124,  -62,    8,
               0, -107,   17,    0,    0,    0,    0,    0,  121,   24,    0,    0, -125,    0,    0,    0,
              26,    3,   76, -112,   70,    2,   19,   68,   62,    8,  114,   35, -109,  123,   75,   35,
               3,   25,   99,   11,  115,   59,    3,  -79,   43, -109, -101,   75,  123,  115,    3, -103,
             113,  -63,  113, -111,  -71,  -87,  -95,  -63, -127,    1,    1,   65,   17,   11, -101,   43,
              35,    3,  121,  115,    3,   97,   98,  -78,  106,    2, -103,  113,  -63,  113, -111,  -71,
             -87,  -95,  -63, -127,   73,   25,   34,  112,    0,   15,  -69,   50,  -71,  -71,  -76,   55,
              55,    6,   49,   67,    8,   78,  -32,    6,   70,  106,   97,  118,   97,   95,  112,   97,
              99,  107,   97,  103,  101,   95,  110,   97,  109,  101,  -44,  -36,  -54,  -24,   92,  -26,
             -34,  -22,  -28,  -58,  -54,  -52,  -34,  -28,  -50,  -54,   92,  -34,  -32,  -54,  -36,  -58,
             -62,  -38,  -54,  -28,  -62, -122,   16,   92,  -63,   25,  108,  -28,  -26,  -66,  -52,  -32,
             -66,  -28,  -54,  -40,  -62,  -16,  -54,  -56,    8,   12,   33,   56, -124,   75,  104, -120,
             -91,  -47,  -75, -123,  -63,   81, -112, -127,   25,   66,  112,   11,  -57, -112, -104,  -85,
             107, -101,   35,  -95, -110,  -26,   70,   55, -124,  -32,   28,  -18,   97,  113, -105,   70,
              70, -121,  -58,  -96,  102,    8,  -63,   69, -100,  -60,    0,  102, -120,  -64,   81,   76,
             -46,  -36,  -46,  -24,  -66,  -26,  -22,  -38,  -26, -122,    8, -100,   69,   66,  -18,  -19,
            -115,  110, -120,  -64,   97, -100,  -58,  -62,  -40,  -58,  -22,  -40,  -62,  -24,  -54,  -66,
             -26,  -48,  -62,  -28,  -32,  -36,  -54,  -26,  -26, -122,    8, -100,   70,  -95,  -26,  102,
            -120,  -64,  113,   28,  -46,  -36,  -24,   -8,  -68,  -75,  -71,  -91,  -63,  -67,  -47, -107,
             -71,  -47, -127, -116,  -95, -123,  -55,   49, -102,   74,  107, -125,   99,   43,    3,   25,
             122,   25,   90,   89,    1,  -95,   18,   10,   10,   26,   34,  112,   96,   48,   68,   24,
            -100,   33,    6,   -9,  113,   97,   48,   56,   67,   12,  -50,  -29,  -58,   96,  112, -122,
              24,   28,   25,  112,  100,   48,   56,   92,  -62,  -36,  -14,   64,  -32,  -34,  -46,  -36,
             -24,  -54,  -28, -122,   24, -100,   25,  112,   99,   48,   56,   67,   12,  -18,   12,  -72,
              51,   24, -100,   33,  -62,  -16,   12,   17,    6,  104, -120,   48,   52,   67,  -80,  -63,
              25,   30,   14,   13, -122,  103,  120,   56,   52,   24,  -96,  -31,  -31,  -48,   96,  104,
            -122, -121,   67, -125,   42,  108,  108,  118,  109,   46,  105,  100,  101,  110,  116,   83,
            -126,  -96,  -57,    8, -100,   92,  -40,   89,   91,  -40, -108, -127,   56, -108,   58,   35,
             114,  115,   95,  101,  120,  112,  111,  114,  116,   95,  118,   97,  114,   83, -122,    6,
            -102,   10, -115,  -56,  -51,  125,  -67, -119,  -87, -107, -115,  -47,  125,  -51,  -79,  -67,
             -47,  -51,   77,    9,  -86,   62,   35,  114,  115,   95,  101,  120,  112,  111,  114,  116,
              95,  102,  117,  110,   99,   83, -126,  -85,  -41, -120,  -36,  -36,   87,   25,   30,  -36,
            -101,   28,  -35, -105,  -39, -101,   92,   89,  -40,   24,  -38, -105,   91,   88,   91,  -39,
              20,   33,  -37,   42, -115,  -56,  -51,  125, -107,  -31,  -63,  -67,  -55,  -47,  125, -103,
             -67,  -55, -107, -123, -115,  -95,   77,   17,  -86,   14,    0,    0,  121,   24,    0,    0,
              92,    0,    0,    0,   51,    8, -128,   28,  -60,  -31,   28,  102,   20,    1,   61, -120,
              67,   56, -124,  -61, -116,   66, -128,    7,  121,  120,    7,  115, -104,  113,   12,  -26,
               0,   15,  -19,   16,   14,  -12, -128,   14,   51,   12,   66,   30,  -62,  -63,   29,  -50,
             -95,   28,  102,   48,    5,   61, -120,   67,   56, -124, -125,   27,  -52,    3,   61,  -56,
              67,   61, -116,    3,   61,  -52,  120, -116,  116,  112,    7,  123,    8,    7,  121,   72,
            -121,  112,  112,    7,  122,  112,    3,  118,  120, -121,  112,   32, -121,   25,  -52,   17,
              14,  -20, -112,   14,  -31,   48,   15,  110,   48,   15,  -29,  -16,   14,  -16,   80,   14,
              51,   16,  -60,   29,  -34,   33,   28,  -40,   33,   29,  -62,   97,   30,  102,   48, -119,
              59,  -68, -125,   59,  -48,   67,   57,  -76,    3,   60,  -68, -125,   60, -124,    3,   59,
             -52,  -16,   20,  118,   96,    7,  123,  104,    7,   55,  104, -121,  114,  104,    7,   55,
            -128, -121,  112, -112, -121,  112,   96,    7,  118,   40,    7,  118,   -8,    5,  118,  120,
            -121,  119, -128, -121,   95,    8, -121,  113,   24, -121,  114, -104, -121,  121, -104, -127,
              44,  -18,  -16,   14,  -18,  -32,   14,  -11,  -64,   14,  -20,   48,    3,   98,  -56,  -95,
              28,  -28,  -95,   28,  -52,  -95,   28,  -28,  -95,   28,  -36,   97,   28,  -54,   33,   28,
             -60, -127,   29,  -54,   97,    6,  -42, -112,   67,   57,  -56,   67,   57, -104,   67,   57,
             -56,   67,   57,  -72,  -61,   56, -108,   67,   56, -120,    3,   59, -108,  -61,   47,  -68,
            -125,   60,   -4, -126,   59,  -44,    3,   59,  -80,  -61,   12,  -57,  105, -121,  112,   88,
            -121,  114,  112, -125,  116,  104,    7,  120,   96, -121,  116,   24, -121,  116,  -96, -121,
              25,  -50,   83,   15,  -18,    0,   15,  -14,   80,   14,  -28, -112,   14,  -29,   64,   15,
             -31,   32,   14,  -20,   80,   14,   51,   32,   40,   29,  -36,  -63,   30,  -62,   65,   30,
             -46,   33,   28,  -36, -127,   30,  -36,  -32,   28,  -28,  -31,   29,  -22,    1,   30,  102,
              24,   81,   56,  -80,   67,   58, -100, -125,   59,  -52,   80,   36,  118,   96,    7,  123,
             104,    7,   55,   96, -121,  119,  120,    7,  120, -104,   81,   76,  -12, -112,   15,  -16,
              80,   14,    0,    0,  113,   32,    0,    0,   48,    0,    0,    0,    6,   80,   88,  -56,
              48,   29,   38,   96,    4,  -56,  -60,    0, -113,   17,   76,    2,  -80,    8,  -44,    2,
              76,  -60,   47,   29,   64,  -12,   52, -124,   36,   89,    0,   33,   81, -116,  100,    5,
             106,  -16,  -49,  118,   29,   73,   32,   49,  121,   11,  -63,   16,  -51,  -92,   77,   63,
              37,   28,   64, -124,  -41,  119,   36,   -3,  -64,  -78,   56,    2,   48,   33,   78, -109,
              36, -122,  112,   -4,  -13,   13,    4,   18,   98,    7,  102,  -16,  -49,  117,   31,   73,
             -38,  -78,   56,    2,   48,   33,   78,    3,   18,   83, -121,   48,  114,  125,   71,  -46,
              15,   44, -117,   35,    0,   19,  -30,   52,  102, -112,   45,   75,  -59,   -8,   12,  -63,
               8,   15,  -26,   63,   52,  -62,   -5,   15, -115,  -16,   62,  -94,  -29,  -90, -128,   -8,
            -111,  -28,   15, -109,   19,  -39,   64, -126,   52,  -56,  -12,   75,   20,   35,   89,  -62,
             -12,  -49,  117,   29,   73,  -38,  -28,   48, -120,  -96,   13,   67,   -6,   34, -120,   45,
            -124,  -63,   63,  -41,  119,   36,  113,   11,    1,   68,  104, -112,   16,  -62, -108,  -42,
             119,   36,   -3,  -64,  -78,   56,    2,   48,   33,   78,    3,    0,   97,   32,    0,    0,
              41,    0,    0,    0,   19,    4,   68,   44,   16,    0,    0,    0,    1,    0,    0,    0,
              52,   74,    0,    0,    4, -116,  -31,    6, -118,   49, -125,   89, -122,   64,  -88,   40,
              32,  -77,    4,    3,   14,   84,   12,   78, -112,   13,  -29,    9,  -42,   53,   24, -127,
              49,    3,   96,  -41,    4,    3,    2,  -58,  105,   67, -122,   27,  -78,   13,   13,  102,
              25,    6, -127,   11,    7,    2,    0,    0,   15,    0,    0,    0,  -58,   49,    8,  -52,
              99,   25,   15,  -46,   12,   21,   16,   73,   62,   82,   -7,   13,  113,   77,   23,   96,
              -8,   75,  -28,   63, -121,   97,   32,   64,   20,    1,   24,   50,   92,   86,   65,    8,
             -52, -125,  -97,  -64,  -30,   -1,   66, -124,   76,   63,   49,   24, -124,   93,   40,   72,
              51,   84,   64,   36,   -7,   72,    5,    0,    1,   49,    0,    0,    5,    0,    0,    0,
              91,    6,   32,   40, -125,   45,  -61,   16,  -96,  -63, -106,    1,    9,  -54,   96,  -53,
             -96,    4,  101,    0,    0,    0,    0,    0,   97,   32,    0,    0,  -89,    0,    0,    0,
              19,    4,   73,   44,   16,    0,    0,    0,    6,    0,    0,    0,    4,   74,  -96,   32,
             106,  -96,    8,   10, -127,   70,    9,   20,   16,  -59,   17,    0, -126,  -74,   70,    0,
               0,    0,    0,    0,   51,   17,    6,   96,   17,   51,   17,    6,   96,   17,   51,   17,
               6,   96,   17,   51,   17,    6,   96,   17,   51,   17,    6,   96,   17,   55,  -75,   29,
              67,   32, -125,  -19,  -54,    0,  -60,  112,    3,  -59, -128,  -63,   44, -125,   16, -100,
              65,   81,   24,  -36,    7,  119,  -60,    0,    1,    0,   35,   13,   60,  -18,  -22,   70,
              12,   12,    3,   40,   62,   52,  -88,  -58,   16,  -44,   96,  -69,   53,    0,   49,   75,
              32,   12,   84,    0,  108,   16, -104,    1,   64,  -64,   48,   55,  -64,   64,   48,  -36,
             -16,    6, -108,   24,  -52,   50,   12,    4,   28,   20,  101,  -63, -127,    1,  -36,   17,
               3,    4,    0,   12,   57,  -16,  -72,  -85,   27,   49,   48,   12,  -96,    0, -125,   56,
             -88,  -58,   16,  -26,   96,   59,   58,    0,   49,   75,   64,   12,   84,    0,  117,   48,
            -104, -127,   48,  -36,   80,   49,   96,   48,  -53,   96,   20,  119,   80,   21,    6,   23,
               6,  112,   71,   12,   16,    0,   48,  -14,  -64,  -29,  -82,  110,  -60,  -64,   48, -128,
              34,   12,   40,   60,   24,   67,  -48, -125,  -19,  -10,    0,  -60,   44, -127,   49,   80,
               1,  -16,   65,  -47,    6,  -60, -119,    1,  -36,   17,    3,    4,    0,   12,   63,  -16,
             -72,  -85,   27,   49,   32,   12,  -32,   16, -125,   -6,    3,   12, -122,   27,   64,  -95,
              18, -125,   89, -122,    3,    9, -123,  -86,   44,  -72,   49, -128,   59,   98, -128,    0,
            -128,   49,   10,   30,  119,  117,   35,    6, -122,    1,   20,   99,   64, -119,  -62,   24,
               2,   41,  108,   87,   10,   32,  102,    9, -112, -127,   10,  -64,   20,   14,   59,   48,
             -52,   12,  -78,   27, -104,   25,   88,   55,  -80,   62,   64, -123,   16, -104,   42, -100,
               2,    8,  108,   21,   82,    1,    4,  -59,   10,   26, -115,   24,   16,    7, -128,  -76,
             -62,   81,   35,   40,   32,  -29,    9,  -80,  -16,   10,   35,    6,    5,    2,   36,  -79,
             -32,   10,   24,   14,    4,    0,    0,    0,   63,    0,    0,    0,  102,  100,    8,   78,
              83,  -39,  -68,  -23,   24,    2,  -80,   44,  -75,  110,   45,  -58,   66,   20, -109,   79,
              91, -113,  -79,   16,  -59,  -28,  -41,  -10,   99,    8,  -64,  -78,  -40,  -76,  -67,   16,
              18,   21,  -28,  118,   50,    8,  -52,   99,   55, -123,  -64,   60,  -75,  109,   68, -122,
               0,   44, -117,  -83,   91,   20,    2,   68,   17, -128,   33,  -61,  101,   79,    7,   50,
              92,   79,  -28,   48, -122,   50,   72,   84,   96,   72,    3,   67,   45, -122,   83,   72,
              84,   80,  -29,   54,   83,    8,  -64,  -78,  -12,   22,   82,   76,  -52,   83,  -25,   38,
              82,   76,  -52,   83,   -9, -106,  100,   20, -128,   48,   57, -111,   57,   25,    2,  -80,
              44,   55,  110,   65, -123,  -64,   60,  -74,  109,   82, -122,    0,   44,  -53,  -99,   27,
               8,   49,   49,   15,  111,   44, -123,  -32,   52,   21,  110,   64, -123,   68,    5,  118,
             109,   66,    5,   48,   12,   54,  110,   48, -124,  -64,   60,  -70,   77,   36,   72,  -29,
              11,   14,   17,    9, -124,  -43,   24, -126,  -45,   84,  117,  109,   38, -124,  -32,   52,
            -107, -115,   20,   19,  -13,  -40,  -71,  -39,   28,   17,   98,   28, -109,   79,   27, -113,
              33,   56,   77,   85,  -13,  -42,   52,   12,   72,  101,   71,   71, -124,   24,  -57,  -28,
             -41,   22,   51,    0,  -61,   96,   74,    5,   48,   12,  119,  109,   75,    5,   48,   12,
             -73,  109,   76, -123,   68,    5,   -9,  109,   31,  -61,  -60,   60,   70,   17,   92,   86,
              17,   96,  -90,   66,    8,  -64,  -78,    0,    1,   49,    0,    0,   10,    0,    0,    0,
              91,    6,  -90,   96, -125,   45, -125,   20, -108,  -63, -106,    1,   43,  -40,   96,  -53,
              32,    6,    5,   27,  108,   25,  -46,  -96,   96, -125,   45,    3,   28,   20,  108,  -80,
             101,  -16, -125,    0,   13,    0,    0,    0,    0,    0,    0,    0,   97,   32,    0,    0,
               3,    0,    0,    0,   19,    4,  -63, -120,    1,   97,    4, -117, -128,    0,    0,    0,
               0,    0,    0,    0,
        };
        return data;
    }

    private static int bitCode64Length = 2612;

    private static byte[] getBitCode64Internal() {
        byte[] bc = new byte[bitCode64Length];
        int offset = 0;
        byte[] seg;
        seg = getSegment64_0();
        System.arraycopy(seg, 0, bc, offset, seg.length);
        offset += seg.length;
        return bc;
    }

}

