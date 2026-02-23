public void addProfilePicture(Context ctx) {
 String userId = ctx.pathParam("userId");
 String destinationDir = "/app/profiles/" + userId;
 try {
 java.io.InputStream zipInput = ctx.uploadedFile("profileZip").content();
 java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(zipInput);
 java.util.zip.ZipEntry entry;
 while ((entry = zis.getNextEntry()) != null) {
 // Vulnerability: Directly using zip entry name without validation
 java.io.File profilePic = new java.io.File(destinationDir, entry.getName());
 // Blindly create directories
 profilePic.getParentFile().mkdirs();
 // Extract the file without path validation
 java.nio.file.Files.copy(
 zis,
profilePic.toPath(),
java.nio.file.StandardCopyOption.REPLACE_EXISTING
 );
 }
 ctx.status(200).json("Profile pictures uploaded successfully");
 } catch (Exception e) {
 ctx.status(500).result("Error uploading profile pictures");
 }
}
