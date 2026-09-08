package io.github.sidneyroberto9.rotom.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomFileUtilsTest {

    private final RotomFileUtils files = new RotomFileUtils();

    @Test
    void toBase64FromFileAndPathEncodeFileContent() throws IOException {
        Path source = Files.createTempFile("rotom-file-test", ".txt");
        Files.write(source, "rotom".getBytes(StandardCharsets.UTF_8));
        String expected = Base64.getEncoder().encodeToString("rotom".getBytes(StandardCharsets.UTF_8));

        assertEquals(expected, files.toBase64(source.toFile()));
        assertEquals(expected, files.toBase64(source.toString()));
    }

    @Test
    void formatSizeUsesBytesBelowOneKilobyte() {
        assertEquals("0 B", files.formatSize(0));
        assertEquals("500 B", files.formatSize(500));
    }

    @Test
    void formatSizeUsesUnitSuffixesAboveOneKilobyte() {
        assertEquals("1.0 KB", files.formatSize(1024));
        assertEquals("1.5 KB", files.formatSize(1536));
        assertEquals("1.0 MB", files.formatSize(1024L * 1024));
    }

    @Test
    void formatSizeThrowsOnNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> files.formatSize(-1));
    }

    @Test
    void moveToWorkspaceMovesFileIntoCurrentWorkingDirectoryKeepingName() throws IOException {
        Path source = Files.createTempFile("rotom-move-test", ".txt");
        File moved = null;

        try {
            moved = files.moveToWorkspace(source.toFile());

            assertEquals(System.getProperty("user.dir"), moved.getParent());
            assertEquals(source.getFileName().toString(), moved.getName());
            assertTrue(moved.exists());
        } finally {
            if (moved != null) {
                Files.deleteIfExists(moved.toPath());
            }

            Files.deleteIfExists(source);
        }
    }

    @Test
    void moveToWorkspaceWithNamePreservesOriginalExtension() throws IOException {
        Path source = Files.createTempFile("rotom-move-test", ".txt");
        File moved = null;

        try {
            moved = files.moveToWorkspace(source.toFile(), "rotom-custom-name");

            assertEquals("rotom-custom-name.txt", moved.getName());
        } finally {
            if (moved != null) {
                Files.deleteIfExists(moved.toPath());
            }

            Files.deleteIfExists(source);
        }
    }

    @Test
    void sanitizeFileNameKeepsOnlySafeCharacters() {
        String sanitized = files.sanitizeFileName("relatório (final) v2!.pdf");

        assertTrue(sanitized.matches("[a-zA-Z0-9._-]+"));
        assertTrue(sanitized.endsWith(".pdf"));
        assertNull(files.sanitizeFileName(null));
    }

    @Test
    void ensureDirectoryExistsCreatesMissingNestedDirectory() throws IOException {
        Path dir = Files.createTempDirectory("rotom-dir-test").resolve("nested/child");

        files.ensureDirectoryExists(dir.toString());

        assertTrue(Files.isDirectory(dir));
    }

    @Test
    void ensureDirectoryExistsDoesNothingWhenAlreadyPresent() throws IOException {
        Path dir = Files.createTempDirectory("rotom-dir-test");

        files.ensureDirectoryExists(dir.toString());

        assertTrue(Files.isDirectory(dir));
    }

    @Test
    void uniqueFileNameAppendsExtensionAndVaries() {
        assertTrue(files.uniqueFileName("pdf").endsWith(".pdf"));
        assertNotEquals(files.uniqueFileName("pdf"), files.uniqueFileName("pdf"));
    }

    @Test
    void uniqueFileNameOmitsExtensionWhenNullOrBlank() {
        assertTrue(!files.uniqueFileName(null).contains("."));
        assertTrue(!files.uniqueFileName("  ").contains("."));
    }
}
