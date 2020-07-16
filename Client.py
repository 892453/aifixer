import os
import sys
import cv2

print(str(sys.argv[1]))
image = cv2.imread(sys.argv[1] , cv2.IMREAD_GRAYSCALE)
cv2.imwrite(sys.argv[1] , image)
