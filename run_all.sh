echo "Vico"
cd vico
time ./run.sh
cd ..
echo ""

echo "FMPy"
cd fmpy
time ./run.sh
cd ..
echo ""

echo "OMSimulator"
cd omsimulator
time ./run.sh
cd ..
echo ""

echo "FMIGo!"
cd fmigo
time ./run.sh
cd ..
echo ""

echo "libcosim!"
cd libcosim
time ./run.sh
cd ..
echo ""


echo "Press 'q' to exit"
count=0
while : ; do
    read -n 1 k <&1
    if [[ $k = q ]] ; then
    printf "\nQuitting from the program\n"
break
else
    echo "Press 'q' to exit"
fi
done