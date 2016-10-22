LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := lame
LOCAL_CFLAGS := -std=c99
LOCAL_LDLIBS := -llog

LOCAL_SRC_FILES := \
lame/bitstream.c \
lame/fft.c \
lame/id3tag.c \
lame/mpglib_interface.c \
lame/presets.c \
lame/quantize.c \
lame/reservoir.c \
lame/tables.c \
lame/util.c \
lame/VbrTag.c \
lame/encoder.c \
lame/gain_analysis.c \
lame/lame.c \
lame/newmdct.c \
lame/psymodel.c \
lame/quantize_pvt.c \
lame/set_get.c \
lame/takehiro.c \
lame/vbrquantize.c \
lame/version.c \
com_tcl_mie_jnidemo_MainActivity.c \
com_tcl_mie_jnidemo_MainActivity.h

include $(BUILD_SHARED_LIBRARY)