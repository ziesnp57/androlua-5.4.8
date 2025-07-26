LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_C_INCLUDES := $(LOCAL_PATH)/../lua
LOCAL_CFLAGS := -std=c17 -O3 -flto \
                -funroll-loops -fomit-frame-pointer \
                -ffunction-sections -fdata-sections \
                -fstrict-aliasing

LOCAL_CFLAGS += -g0 -DNDEBUG

# 极致性能构建配置
LOCAL_CFLAGS += -fno-exceptions -fno-unwind-tables -fno-asynchronous-unwind-tables

# 链接选项
LOCAL_LDFLAGS := -flto -fuse-linker-plugin -Wl,--gc-sections

LOCAL_MODULE     := lfs
LOCAL_SRC_FILES  := lfs.c

LOCAL_STATIC_LIBRARIES := luajava
include $(BUILD_SHARED_LIBRARY)
