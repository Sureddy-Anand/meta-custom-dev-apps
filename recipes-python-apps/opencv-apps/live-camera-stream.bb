SUMMARY = "OpenCV-based Camera Application"
DESCRIPTION = "A Python script that captures video using OpenCV and streams it via GStreamer."
LICENSE = "CLOSED"
#LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835cf2592b36cd8ef1d8c20a97a14d3"

SRC_URI = "file://live_camera_stream.py"

S = "${WORKDIR}"

DEPENDS = "opencv gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good"

RDEPENDS_${PN} = "python3 python3-opencv gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-good"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/live_camera_stream.py ${D}${bindir}/live_camera_stream
}

FILES_${PN} += "${bindir}/live_camera_stream"

