package com.example.mpbackend.masterpiece

data class MPBackendContribution(val songId: Long, val title: String, val snippetContributions: List<MPSnippetContribution>)