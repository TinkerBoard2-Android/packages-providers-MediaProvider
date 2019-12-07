/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.providers.media.util;

import android.content.ClipDescription;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.Locale;

public class MimeUtils {
    /**
     * Resolve the MIME type of the given file, returning
     * {@code application/octet-stream} if the type cannot be determined.
     */
    public static @NonNull String resolveMimeType(@NonNull File file) {
        final String extension = FileUtils.extractFileExtension(file.getPath());
        if (extension == null) return ClipDescription.MIMETYPE_UNKNOWN;

        final String mimeType = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(extension.toLowerCase(Locale.ROOT));
        if (mimeType == null) return ClipDescription.MIMETYPE_UNKNOWN;

        return mimeType;
    }

    public static @NonNull String extractPrimaryType(@NonNull String mimeType) {
        final int slash = mimeType.indexOf('/');
        if (slash == -1) {
            throw new IllegalArgumentException();
        }
        return mimeType.substring(0, slash);
    }

    public static boolean isAudioMimeType(@Nullable String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("audio/");
    }

    public static boolean isVideoMimeType(@Nullable String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("video/");
    }

    public static boolean isImageMimeType(@Nullable String mimeType) {
        if (mimeType == null) return false;
        return mimeType.startsWith("image/");
    }

    public static boolean isPlaylistMimeType(@Nullable String mimeType) {
        if (mimeType == null) return false;
        switch (mimeType) {
            case "application/vnd.apple.mpegurl":
            case "application/vnd.ms-wpl":
            case "application/x-mpegurl":
            case "audio/mpegurl":
            case "audio/x-mpegurl":
            case "audio/x-scpls":
                return true;
            default:
                return false;
        }
    }
}