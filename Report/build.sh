rm main.aux main.bbl main.blg main.log main.toc
pdflatex -interaction=nonstopmode main.tex
bibtex main.aux
pdflatex -interaction=nonstopmode main.tex
pdflatex -interaction=nonstopmode main.tex
