<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="UploadedMaterial">
		<xsl:param name="entryName">
			Uploaded Materials
		</xsl:param>

		<table width="100%" border="0" cellspacing="0" cellpadding="3">
			<tr valign="top">
				<td class="elemPresentationCell1">
					<xsl:value-of select="$entryName" />
					:
					<xsl:text> </xsl:text>
					<!--<xsl:value-of select="entryName/value"/> -->
					<xsl:value-of select="sampleName/value" />
				</td>
				<td class="elemPresentationCell2">
					<strong>Name:</strong>
					<xsl:value-of select="sampleName/value" />
					<br />
					<xsl:variable name="fileURL">
						GetFile?entryId=
						<xsl:value-of select="entryId/value" />
						&amp;euid=
						<xsl:value-of select="encryptedPersonId/value" />
					</xsl:variable>
					<xsl:choose>
						<xsl:when test="type/value = 'file'">
							<strong>File:</strong>
							<a target="ospiMaterial">
								<xsl:attribute name="href">
                                        <xsl:value-of
									select="$fileURL" />
                                    </xsl:attribute>
								<xsl:value-of select="name/value" />
							</a>
							<br />
							<strong>Size:</strong>
							<xsl:value-of select="sampleSizeMB/value" />
							MB
						</xsl:when>
						<xsl:otherwise>
							<strong>URL:</strong>
							<a target="ospiMaterial">
								<xsl:attribute name="href">
                                        <xsl:value-of
									select="name/value" />
                                    </xsl:attribute>
								<xsl:value-of select="name/value" />
							</a>
						</xsl:otherwise>
					</xsl:choose>
				</td>
				<td class="elemPresentationCell3">
					<xsl:choose>
						<xsl:when test="elementMaterials/material">
							<xsl:apply-templates select="elementMaterials" />
						</xsl:when>
						<xsl:otherwise>
							<font class="invisible">[No Materials]</font>
						</xsl:otherwise>
					</xsl:choose>
				</td>
			</tr>
		</table>

	</xsl:template>
</xsl:stylesheet>
