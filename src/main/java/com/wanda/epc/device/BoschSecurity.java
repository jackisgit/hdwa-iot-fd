package com.wanda.epc.device;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public class BoschSecurity {

    public static interface BS7400CtlDLL extends StdCallLibrary {
        public static final BS7400CtlDLL instance = (BS7400CtlDLL) Native.loadLibrary("BS7400Ctl", BS7400CtlDLL.class);

        Pointer New_Object();

        void ArrangeRcvAddress(Pointer param1Pointer, String param1String1, String param1String2);

        int OpenReceiver(Pointer param1Pointer1, Trandataproc param1Trandataproc, Pointer param1Pointer2);

        boolean CanControlPanel(Pointer param1Pointer);

        boolean CanReceiveEvent(Pointer param1Pointer);

        int Execute(Pointer param1Pointer, String param1String1, String param1String2, String param1String3, int param1Int);

        boolean SetPanelControlCodes(Pointer param1Pointer, String param1String1, String param1String2, String param1String3);

        void CloseReciever(Pointer param1Pointer);

        void Delete_Object(Pointer param1Pointer);

        void SetLnkIntval(Pointer param1Pointer, int param1Int);
    }

    public static interface Trandataproc extends StdCallLibrary.StdCallCallback {
        void invoke(Pointer param1Pointer, String param1String, int param1Int);
    }

}
