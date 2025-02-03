#!/usr/bin/env python3
import cv2

cap = cv2.VideoCapture(0)
gst_pipeline = "appsrc ! videoconvert ! autovideosink sync=false"
out = cv2.VideoWriter(gst_pipeline, cv2.CAP_GSTREAMER, 0, 30, (640, 480))

while True:
    ret, frame = cap.read()
    if not ret:
        break

    out.write(frame)

cap.release()
out.release()

