<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="elementMaterials">
		<xsl:choose>
			<xsl:when test="count(material) > 0">
				<strong>Attachments:</strong>
				<br />
				<xsl:for-each select="material">
					<xsl:if test="position() > 1">
						<br />
						<br />
					</xsl:if>
					<xsl:variable name="fileURL">
						GetFile?entryId=
						<xsl:value-of select="entryId/value" />
						&amp;euid=
						<xsl:value-of select="encryptedPersonId/value" />
					</xsl:variable>
					<xsl:if test="isImage/value = &quot;true&quot;">
						<img height="120">
							<xsl:attribute name="src"><xsl:value-of
								select="$fileURL" /></xsl:attribute>
						</img>
					</xsl:if>
					<br />
					<a target="ospiMaterial">
						<!--<xsl:attribute name="href"><xsl:value-of select="showMaterialUrl/value"/></xsl:attribute> -->
						<xsl:choose>
							<xsl:when test="url/value = &quot;true&quot;">
								<xsl:attribute name="href"><xsl:value-of
									select="name/value" /></xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="href"><xsl:value-of
									select="$fileURL" /></xsl:attribute>
							</xsl:otherwise>
						</xsl:choose>
						<text>- </text>
						<xsl:value-of select="sampleName/value" />
					</a>
					(
					<xsl:value-of select="name/value" />
					)
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<font class="invisible">[No Materials]</font>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
