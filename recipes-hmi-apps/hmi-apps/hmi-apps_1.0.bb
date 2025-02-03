DESCRIPTION = "HMI-Apps recipe to build all the collective lvgl application examples"
SECTION = "HMI-Apps"
LICENSE = "CLOSED"

SRC_URI = "https://github.com/Sureddy-Anand/renesas-hmi-apps.git;branch=master;protocol=https"


# Optionally, you can specify the directory where the repository should be cloned
# and which branch/commit should be used
SRCREV = "master"  

# Specify the directory to clone the repository into
S = "${WORKDIR}"
HMI_APPS = "${WORKDIR}/renesas-hmi-apps"
LVGL_AUDIO_PLAYBACK = "${S}/lvgl-audio-playback"
LVGL_VIDEO_PLAYBACK = "${S}/lvgl-video-playback"
LVGL_IMAGE_DISPLAY = "${S}/lvgl-image-display"
SOUNDS = "${LVGL_AUDIO_PLAYBACK}/sample"
IMAGES = "${LVGL_IMAGE_DISPLAY}/sample"
MOVIES = "${LVGL_VIDEO_PLAYBACK}/sample"

LVGL_AUDIO_PLAYBACK_BUILD = "${S}/lvgl-audio-playback/build"
LVGL_VIDEO_PLAYBACK_BUILD = "${S}/lvgl-video-playback/build"
LVGL_IMAGE_DISPLAY_BUILD = "${S}/lvgl-image-display/build"

FILES_${PN} += "/usr/share/images"
FILES_${PN} += "/usr/share/movies"

inherit autotools

DEPENDS = "lvgl lv-drivers gstreamer1.0 wayland glib-2.0 "

do_fetch() {
    echo ${S}
    # cd ${S}
    # Ensure that the downloaded source is present
    if [ ! -d "${DL_DIR}/renesas-hmi-apps" ]; then
        bbwarn "Downloading renesas-hmi-apps!!"
        git clone ${SRC_URI} 
    fi
}

do_unpack() {
    # Ensure that the downloaded source is present
    if [ ! -d "${DL_DIR}/renesas-hmi-apps" ]; then
        bbwarn "Downloaded source not found! Please ensure that the fetch step completed successfully."
        return 1
    fi
    # Create the destination directory in the work directory
    mkdir -p ${S}
    # Copy the downloaded repository folder into the work directory
    cp -R ${DL_DIR}/renesas-hmi-apps/* ${S}/
    #rm -R ${DL_DIR}/renesas-hmi-apps/
}


#do_patch_image_display() {
# Apply patch to the src folder
#    patch -p2 -d ${LVGL_IMAGE_DISPLAY}/src < ${LVGL_IMAGE_DISPLAY}/0001-second-commit.patch
#}

#do_patch() {
#    do_patch_image_display
#}

# Build step using Make
do_compile_audio_playback() {
    # Set up cross-compiler environment variables
    cd ${LVGL_AUDIO_PLAYBACK} 
    # Run Make
    oe_runmake
}

# Build step using Make
do_compile_video_playback() {
    # Set up cross-compiler environment variables
    cd ${LVGL_VIDEO_PLAYBACK} 
    # Run Make
    oe_runmake
}

# Build step using Make
do_compile_image_display() {
    # Set up cross-compiler environment variables
    cd ${LVGL_IMAGE_DISPLAY} 
    # Run Make
    oe_runmake
}

do_compile() {
    export TARGET_CC="${CC}"
    export includedir="${STAGING_INCDIR}"
   
    do_compile_audio_playback
    do_compile_video_playback
    do_compile_image_display
}

do_install_audio_playback() {
    install -d ${D}${bindir}
    install -m 0755 ${LVGL_AUDIO_PLAYBACK_BUILD}/lvgl_sample_audio_playback  ${D}${bindir}/lvgl_sample_audio_playback 
    install -d ${D}/usr/share/sounds/sample
    install -m 0755 ${SOUNDS}/*  ${D}/usr/share/sounds/sample 
}


do_install_image_display() {
    install -d ${D}${bindir}
    install -m 0755 ${LVGL_IMAGE_DISPLAY_BUILD}/lvgl_sample_img_display  ${D}${bindir}/lvgl_sample_img_display 
    install -d ${D}/usr/share/images
    install -m 0755 ${IMAGES}/*  ${D}/usr/share/images 
}

do_install_video_playback() {
    install -d ${D}${bindir}
    install -m 0755 ${LVGL_VIDEO_PLAYBACK_BUILD}/lvgl_sample_video_playback  ${D}${bindir}/lvgl_sample_video_playback 
    install -d ${D}/usr/share/movies
    install -m 0755 ${MOVIES}/*  ${D}/usr/share/movies 
}

do_install() {
    do_install_audio_playback
    do_install_image_display
    do_install_video_playback
}
